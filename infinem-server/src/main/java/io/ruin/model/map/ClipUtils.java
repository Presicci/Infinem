package io.ruin.model.map;

import io.ruin.model.map.route.RouteFinder;

public class ClipUtils {

    public static final ClipUtils NONE = new ClipUtils(); //always returns 0.. this isn't reliable at all but I need it for now..

    public static final ClipUtils REGULAR = new ClipUtils() {
        @Override
        public int getAbs(int absX, int absY, int z) {
            Region region = Region.get(absX, absY);
            if(region.empty)
                return RouteFinder.UNMOVABLE_MASK;
            Tile tile = region.getTile(absX, absY, z, false);
            return tile == null ? 0 : tile.clipping;
        }
    };

    public static final ClipUtils FLIGHT = new ClipUtils() {
        @Override
        public int getAbs(int absX, int absY, int z) {
            Region region = Region.get(absX, absY);
            if(region.empty)
                return RouteFinder.UNMOVABLE_MASK;
            Tile tile = region.getTile(absX, absY, z, false);
            return tile == null ? 0 : tile.projectileClipping;
        }
    };

    public static final ClipUtils SWIM = new ClipUtils() {
        @Override
        public int getAbs(int absX, int absY, int z) {
            Region region = Region.get(absX, absY);
            if(region.empty)
                return RouteFinder.UNMOVABLE_MASK;
            Tile tile = region.getTile(absX, absY, z, false);
            return (tile == null || tile.clipping == 0 || (tile.clipping & RouteFinder.UNMOVABLE_MASK) == 0) ? RouteFinder.UNMOVABLE_MASK : (tile.clipping & ~RouteFinder.UNMOVABLE_MASK);
        }
    };

    public int baseX, baseY, z;

    public void update(int baseX, int baseY, int z) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.z = z;
    }

    public int get(int localX, int localY) {
        return getAbs(baseX + localX, baseY + localY, z);
    }

    public int getAbs(int absX, int absY, int z) {
        /* override required */
        return 0;
    }

    /**
     * Clipping
     */

    public static void addClipping(int x, int y, int z, int mask, boolean projectile) {
        Tile tile = Tile.get(x, y, z, true);
        if(projectile)
            tile.projectileClipping |= mask;
        else
            tile.clipping |= mask;
    }

    public static void addClipping(int x, int y, int z, int xLength, int yLength, boolean solid, boolean projectile) {
        int mask = 256;
        if(solid)
            mask |= 0x20000;
        for(int a = x; a < x + xLength; a++) {
            for(int b = y; b < y + yLength; b++)
                addClipping(a, b, z, mask, projectile);
        }
    }

    public static void removeClipping(int x, int y, int z, int mask, boolean projectile) {
        Tile tile = Tile.get(x, y, z, true);
        if(projectile)
            tile.projectileClipping &= ~mask;
        else
            tile.clipping &= ~mask;
    }

    public static void removeClipping(int x, int y, int z, int xLength, int yLength, boolean solid, boolean projectile) {
        int mask = 256;
        if(solid)
            mask |= 0x20000;
        for(int a = x; a < x + xLength; a++) {
            for(int b = y; b < y + yLength; b++)
                removeClipping(a, b, z, mask, projectile);
        }
    }

    public static void addVariableClipping(int x, int y, int z, int type, int direction, boolean solid, boolean projectile) {
        if(type == 0) {
            if(direction == 0) {
                addClipping(x, y, z, 128, projectile);
                addClipping(x - 1, y, z, 8, projectile);
            }
            if(direction == 1) {
                addClipping(x, y, z, 2, projectile);
                addClipping(x, y + 1, z, 32, projectile);
            }
            if(direction == 2) {
                addClipping(x, y, z, 8, projectile);
                addClipping(x + 1, y, z, 128, projectile);
            }
            if(direction == 3) {
                addClipping(x, y, z, 32, projectile);
                addClipping(x, y - 1, z, 2, projectile);
            }
        }
        if(type == 1 || type == 3) {
            if(direction == 0) {
                addClipping(x, y, z, 1, projectile);
                addClipping(x - 1, y + 1, z, 16, projectile);
            }
            if(direction == 1) {
                addClipping(x, y, z, 4, projectile);
                addClipping(x + 1, y + 1, z, 64, projectile);
            }
            if(direction == 2) {
                addClipping(x, y, z, 16, projectile);
                addClipping(x + 1, y - 1, z, 1, projectile);
            }
            if(direction == 3) {
                addClipping(x, y, z, 64, projectile);
                addClipping(x - 1, y - 1, z, 4, projectile);
            }
        }
        if(type == 2) {
            if(direction == 0) {
                addClipping(x, y, z, 130, projectile);
                addClipping(x - 1, y, z, 8, projectile);
                addClipping(x, y + 1, z, 32, projectile);
            }
            if(direction == 1) {
                addClipping(x, y, z, 10, projectile);
                addClipping(x, y + 1, z, 32, projectile);
                addClipping(x + 1, y, z, 128, projectile);
            }
            if(direction == 2) {
                addClipping(x, y, z, 40, projectile);
                addClipping(x + 1, y, z, 128, projectile);
                addClipping(x, y - 1, z, 2, projectile);
            }
            if(direction == 3) {
                addClipping(x, y, z, 160, projectile);
                addClipping(x, y - 1, z, 2, projectile);
                addClipping(x - 1, y, z, 8, projectile);
            }
        }
        if(solid) {
            if(type == 0) {
                if(direction == 0) {
                    addClipping(x, y, z, 65536, projectile);
                    addClipping(x - 1, y, z, 4096, projectile);
                }
                if(direction == 1) {
                    addClipping(x, y, z, 1024, projectile);
                    addClipping(x, y + 1, z, 16384, projectile);
                }
                if(direction == 2) {
                    addClipping(x, y, z, 4096, projectile);
                    addClipping(x + 1, y, z, 65536, projectile);
                }
                if(direction == 3) {
                    addClipping(x, y, z, 16384, projectile);
                    addClipping(x, y - 1, z, 1024, projectile);
                }
            }
            if(type == 1 || type == 3) {
                if(direction == 0) {
                    addClipping(x, y, z, 512, projectile);
                    addClipping(x - 1, y + 1, z, 8192, projectile);
                }
                if(direction == 1) {
                    addClipping(x, y, z, 2048, projectile);
                    addClipping(x + 1, y + 1, z, 32768, projectile);
                }
                if(direction == 2) {
                    addClipping(x, y, z, 8192, projectile);
                    addClipping(x + 1, y - 1, z, 512, projectile);
                }
                if(direction == 3) {
                    addClipping(x, y, z, 32768, projectile);
                    addClipping(x - 1, y - 1, z, 2048, projectile);
                }
            }
            if(type == 2) {
                if(direction == 0) {
                    addClipping(x, y, z, 66560, projectile);
                    addClipping(x - 1, y, z, 4096, projectile);
                    addClipping(x, y + 1, z, 16384, projectile);
                }
                if(direction == 1) {
                    addClipping(x, y, z, 5120, projectile);
                    addClipping(x, y + 1, z, 16384, projectile);
                    addClipping(x + 1, y, z, 65536, projectile);
                }
                if(direction == 2) {
                    addClipping(x, y, z, 20480, projectile);
                    addClipping(x + 1, y, z, 65536, projectile);
                    addClipping(x, y - 1, z, 1024, projectile);
                }
                if(direction == 3) {
                    addClipping(x, y, z, 81920, projectile);
                    addClipping(x, y - 1, z, 1024, projectile);
                    addClipping(x - 1, y, z, 4096, projectile);
                }
            }
        }
    }

    public static void removeVariableClipping(int x, int y, int z, int type, int direction, boolean solid, boolean projectile) {
        if(type == 0) {
            if(direction == 0) {
                removeClipping(x, y, z, 128, projectile);
                removeClipping(x - 1, y, z, 8, projectile);
            }
            if(direction == 1) {
                removeClipping(x, y, z, 2, projectile);
                removeClipping(x, y + 1, z, 32, projectile);
            }
            if(direction == 2) {
                removeClipping(x, y, z, 8, projectile);
                removeClipping(x + 1, y, z, 128, projectile);
            }
            if(direction == 3) {
                removeClipping(x, y, z, 32, projectile);
                removeClipping(x, y - 1, z, 2, projectile);
            }
        }
        if(type == 1 || type == 3) {
            if(direction == 0) {
                removeClipping(x, y, z, 1, projectile);
                removeClipping(x - 1, y + 1, z, 16, projectile);
            }
            if(direction == 1) {
                removeClipping(x, y, z, 4, projectile);
                removeClipping(x + 1, y + 1, z, 64, projectile);
            }
            if(direction == 2) {
                removeClipping(x, y, z, 16, projectile);
                removeClipping(x + 1, y - 1, z, 1, projectile);
            }
            if(direction == 3) {
                removeClipping(x, y, z, 64, projectile);
                removeClipping(x - 1, y - 1, z, 4, projectile);
            }
        }
        if(type == 2) {
            if(direction == 0) {
                removeClipping(x, y, z, 130, projectile);
                removeClipping(x - 1, y, z, 8, projectile);
                removeClipping(x, y + 1, z, 32, projectile);
            }
            if(direction == 1) {
                removeClipping(x, y, z, 10, projectile);
                removeClipping(x, y + 1, z, 32, projectile);
                removeClipping(x + 1, y, z, 128, projectile);
            }
            if(direction == 2) {
                removeClipping(x, y, z, 40, projectile);
                removeClipping(x + 1, y, z, 128, projectile);
                removeClipping(x, y - 1, z, 2, projectile);
            }
            if(direction == 3) {
                removeClipping(x, y, z, 160, projectile);
                removeClipping(x, y - 1, z, 2, projectile);
                removeClipping(x - 1, y, z, 8, projectile);
            }
        }
        if(solid) {
            if(type == 0) {
                if(direction == 0) {
                    removeClipping(x, y, z, 65536, projectile);
                    removeClipping(x - 1, y, z, 4096, projectile);
                }
                if(direction == 1) {
                    removeClipping(x, y, z, 1024, projectile);
                    removeClipping(x, y + 1, z, 16384, projectile);
                }
                if(direction == 2) {
                    removeClipping(x, y, z, 4096, projectile);
                    removeClipping(x + 1, y, z, 65536, projectile);
                }
                if(direction == 3) {
                    removeClipping(x, y, z, 16384, projectile);
                    removeClipping(x, y - 1, z, 1024, projectile);
                }
            }
            if(type == 1 || type == 3) {
                if(direction == 0) {
                    removeClipping(x, y, z, 512, projectile);
                    removeClipping(x - 1, y + 1, z, 8192, projectile);
                }
                if(direction == 1) {
                    removeClipping(x, y, z, 2048, projectile);
                    removeClipping(x + 1, y + 1, z, 32768, projectile);
                }
                if(direction == 2) {
                    removeClipping(x, y, z, 8192, projectile);
                    removeClipping(x + 1, y - 1, z, 512, projectile);
                }
                if(direction == 3) {
                    removeClipping(x, y, z, 32768, projectile);
                    removeClipping(x - 1, y - 1, z, 2048, projectile);
                }
            }
            if(type == 2) {
                if(direction == 0) {
                    removeClipping(x, y, z, 66560, projectile);
                    removeClipping(x - 1, y, z, 4096, projectile);
                    removeClipping(x, y + 1, z, 16384, projectile);
                }
                if(direction == 1) {
                    removeClipping(x, y, z, 5120, projectile);
                    removeClipping(x, y + 1, z, 16384, projectile);
                    removeClipping(x + 1, y, z, 65536, projectile);
                }
                if(direction == 2) {
                    removeClipping(x, y, z, 20480, projectile);
                    removeClipping(x + 1, y, z, 65536, projectile);
                    removeClipping(x, y - 1, z, 1024, projectile);
                }
                if(direction == 3) {
                    removeClipping(x, y, z, 81920, projectile);
                    removeClipping(x, y - 1, z, 1024, projectile);
                    removeClipping(x - 1, y, z, 4096, projectile);
                }
            }
        }
    }

    /**
     * Misc
     */

    public boolean method3065(int startX, int startY, int size, int checkX, int checkY, int type, int direction) {
        if(1 == size) {
            if(startX == checkX && startY == checkY)
                return true;
        } else if(checkX >= startX && checkX <= size + startX - 1 && checkY >= checkY && checkY <= size + checkY - 1)
            return true;
        if(size == 1) {
            if(type == 0) {
                if(direction == 0) {
                    if(checkX - 1 == startX && startY == checkY)
                        return true;
                    if(startX == checkX && 1 + checkY == startY && 0 == (get(startX, startY) & RouteFinder.NORTH_MASK))
                        return true;
                    if(startX == checkX && checkY - 1 == startY && 0 == (get(startX, startY) & RouteFinder.SOUTH_MASK))
                        return true;
                } else if(direction == 1) {
                    if(checkX == startX && 1 + checkY == startY)
                        return true;
                    if(checkX - 1 == startX && checkY == startY && (get(startX, startY) & RouteFinder.WEST_MASK) == 0)
                        return true;
                    if(checkX + 1 == startX && startY == checkY && 0 == (get(startX, startY) & RouteFinder.EAST_MASK))
                        return true;
                } else if(direction == 2) {
                    if(startX == checkX + 1 && checkY == startY)
                        return true;
                    if(checkX == startX && 1 + checkY == startY && 0 == (get(startX, startY) & RouteFinder.NORTH_MASK))
                        return true;
                    if(checkX == startX && checkY - 1 == startY && (get(startX, startY) & RouteFinder.SOUTH_MASK) == 0)
                        return true;
                } else if(3 == direction) {
                    if(startX == checkX && startY == checkY - 1)
                        return true;
                    if(startX == checkX - 1 && checkY == startY && (get(startX, startY) & RouteFinder.WEST_MASK) == 0)
                        return true;
                    if(startX == 1 + checkX && startY == checkY && (get(startX, startY) & RouteFinder.EAST_MASK) == 0)
                        return true;
                }
            }
            if(2 == type) {
                if(0 == direction) {
                    if(startX == checkX - 1 && checkY == startY)
                        return true;
                    if(checkX == startX && startY == 1 + checkY)
                        return true;
                    if(checkX + 1 == startX && checkY == startY && (get(startX, startY) & RouteFinder.EAST_MASK) == 0)
                        return true;
                    if(startX == checkX && startY == checkY - 1 && 0 == (get(startX, startY) & RouteFinder.SOUTH_MASK))
                        return true;
                } else if(1 == direction) {
                    if(checkX - 1 == startX && startY == checkY && 0 == (get(startX, startY) & RouteFinder.WEST_MASK))
                        return true;
                    if(startX == checkX && 1 + checkY == startY)
                        return true;
                    if(startX == checkX + 1 && checkY == startY)
                        return true;
                    if(checkX == startX && startY == checkY - 1 && (get(startX, startY) & RouteFinder.SOUTH_MASK) == 0)
                        return true;
                } else if(direction == 2) {
                    if(checkX - 1 == startX && checkY == startY && (get(startX, startY) & RouteFinder.WEST_MASK) == 0)
                        return true;
                    if(startX == checkX && startY == 1 + checkY && 0 == (get(startX, startY) & RouteFinder.NORTH_MASK))
                        return true;
                    if(startX == 1 + checkX && startY == checkY)
                        return true;
                    if(checkX == startX && startY == checkY - 1)
                        return true;
                } else if(direction == 3) {
                    if(startX == checkX - 1 && checkY == startY)
                        return true;
                    if(startX == checkX && 1 + checkY == startY && (get(startX, startY) & RouteFinder.NORTH_MASK) == 0)
                        return true;
                    if(1 + checkX == startX && checkY == startY && (get(startX, startY) & RouteFinder.EAST_MASK) == 0)
                        return true;
                    if(startX == checkX && startY == checkY - 1)
                        return true;
                }
            }
            if(9 == type) {
                if(checkX == startX && startY == checkY + 1 && 0 == (get(startX, startY) & 0x20))
                    return true;
                if(startX == checkX && startY == checkY - 1 && (get(startX, startY) & 0x2) == 0)
                    return true;
                if(checkX - 1 == startX && checkY == startY && (get(startX, startY) & 0x8) == 0)
                    return true;
                if(startX == checkX + 1 && startY == checkY && 0 == (get(startX, startY) & 0x80))
                    return true;
            }
        } else {
            int i_44_ = size + startX - 1;
            int i_45_ = startY + size - 1;
            if(type == 0) {
                if(0 == direction) {
                    if(startX == checkX - size && checkY >= startY && checkY <= i_45_)
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && startY == 1 + checkY && ((get(checkX, startY) & RouteFinder.NORTH_MASK) == 0))
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && startY == checkY - size && ((get(checkX, i_45_) & RouteFinder.SOUTH_MASK) == 0))
                        return true;
                } else if(1 == direction) {
                    if(checkX >= startX && checkX <= i_44_ && startY == 1 + checkY)
                        return true;
                    if(startX == checkX - size && checkY >= startY && checkY <= i_45_ && ((get(i_44_, checkY) & RouteFinder.WEST_MASK) == 0))
                        return true;
                    if(startX == 1 + checkX && checkY >= startY && checkY <= i_45_ && (get(startX, checkY) & RouteFinder.EAST_MASK) == 0)
                        return true;
                } else if(2 == direction) {
                    if(1 + checkX == startX && checkY >= startY && checkY <= i_45_)
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && startY == checkY + 1 && 0 == (get(checkX, startY) & RouteFinder.NORTH_MASK))
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && checkY - size == startY && 0 == (get(checkX, i_45_) & RouteFinder.SOUTH_MASK))
                        return true;
                } else if(direction == 3) {
                    if(checkX >= startX && checkX <= i_44_ && startY == checkY - size)
                        return true;
                    if(startX == checkX - size && checkY >= startY && checkY <= i_45_ && 0 == (get(i_44_, checkY) & RouteFinder.WEST_MASK))
                        return true;
                    if(startX == checkX + 1 && checkY >= startY && checkY <= i_45_ && 0 == (get(startX, checkY) & RouteFinder.EAST_MASK))
                        return true;
                }
            }
            if(2 == type) {
                if(0 == direction) {
                    if(startX == checkX - size && checkY >= startY && checkY <= i_45_)
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && 1 + checkY == startY)
                        return true;
                    if(1 + checkX == startX && checkY >= startY && checkY <= i_45_ && 0 == (get(startX, checkY) & RouteFinder.EAST_MASK))
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && checkY - size == startY && ((get(checkX, i_45_) & RouteFinder.SOUTH_MASK) == 0))
                        return true;
                } else if(1 == direction) {
                    if(startX == checkX - size && checkY >= startY && checkY <= i_45_ && 0 == (get(i_44_, checkY) & RouteFinder.WEST_MASK))
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && startY == checkY + 1)
                        return true;
                    if(startX == checkX + 1 && checkY >= startY && checkY <= i_45_)
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && startY == checkY - size && ((get(checkX, i_45_) & RouteFinder.SOUTH_MASK) == 0))
                        return true;
                } else if(direction == 2) {
                    if(startX == checkX - size && checkY >= startY && checkY <= i_45_ && ((get(i_44_, checkY) & RouteFinder.WEST_MASK) == 0))
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && startY == checkY + 1 && 0 == (get(checkX, startY) & RouteFinder.NORTH_MASK))
                        return true;
                    if(startX == 1 + checkX && checkY >= startY && checkY <= i_45_)
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && startY == checkY - size)
                        return true;
                } else if(3 == direction) {
                    if(checkX - size == startX && checkY >= startY && checkY <= i_45_)
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && checkY + 1 == startY && 0 == (get(checkX, startY) & RouteFinder.NORTH_MASK))
                        return true;
                    if(startX == checkX + 1 && checkY >= startY && checkY <= i_45_ && 0 == (get(startX, checkY) & RouteFinder.EAST_MASK))
                        return true;
                    if(checkX >= startX && checkX <= i_44_ && startY == checkY - size)
                        return true;
                }
            }
            if(9 == type) {
                if(checkX >= startX && checkX <= i_44_ && startY == checkY + 1 && (get(checkX, startY) & RouteFinder.NORTH_MASK) == 0)
                    return true;
                if(checkX >= startX && checkX <= i_44_ && checkY - size == startY && 0 == (get(checkX, i_45_) & RouteFinder.SOUTH_MASK))
                    return true;
                if(checkX - size == startX && checkY >= startY && checkY <= i_45_ && (get(i_44_, checkY) & RouteFinder.WEST_MASK) == 0)
                    return true;
                if(1 + checkX == startX && checkY >= startY && checkY <= i_45_ && (get(startX, checkY) & RouteFinder.EAST_MASK) == 0)
                    return true;
            }
        }
        return false;
    }

    public boolean method3066(int x, int y, int size, int destX, int destY, int xLength, int yLength, int objectDirectionClip) {
        if(size > 1) {
            if(withDistance(x, y, destX, destY, size, size, xLength, yLength))
                return true;
            return canStep(x, y, size, size, destX, destY, xLength, yLength, objectDirectionClip);
        }
        int xAndLength = destX + xLength - 1;
        int yAndLength = destY + yLength - 1;
        if(x >= destX && x <= xAndLength && y >= destY && y <= yAndLength)
            return true;
        if(x == destX - 1 && y >= destY && y <= yAndLength && ((get(x, y)) & 0x8) == 0 && 0 == (objectDirectionClip & 0x8))
            return true;
        if(xAndLength + 1 == x && y >= destY && y <= yAndLength && ((get(x, y)) & 0x80) == 0 && (objectDirectionClip & 0x2) == 0)
            return true;
        if(destY - 1 == y && x >= destX && x <= xAndLength && 0 == ((get(x, y)) & 0x2) && (objectDirectionClip & 0x4) == 0)
            return true;
        if(y == 1 + yAndLength && x >= destX && x <= xAndLength && 0 == ((get(x, y)) & 0x20) && (objectDirectionClip & 0x1) == 0)
            return true;
        return false;
    }

    public boolean canStep(int checkX, int checkY, int xLength, int yLength, int startX, int startY, int i_65_, int i_66_, int objectDirectionClip) {
        int i_69_ = checkX + xLength;
        int i_70_ = yLength + checkY;
        int i_71_ = startX + i_65_;
        int i_72_ = startY + i_66_;
        if(checkX == i_71_ && 0 == (objectDirectionClip & 0x2)) {
            int i_73_ = checkY > startY ? checkY : startY;
            for(int i_74_ = i_70_ < i_72_ ? i_70_ : i_72_; i_73_ < i_74_; i_73_++) {
                if(0 == ((get(i_71_ - 1, i_73_)) & 0x8))
                    return true;
            }
        } else if(startX == i_69_ && (objectDirectionClip & 0x8) == 0) {
            int i_75_ = checkY > startY ? checkY : startY;
            for(int i_76_ = i_70_ < i_72_ ? i_70_ : i_72_; i_75_ < i_76_; i_75_++) {
                if(0 == ((get(startX, i_75_)) & 0x80))
                    return true;
            }
        } else if(checkY == i_72_ && (objectDirectionClip & 0x1) == 0) {
            int i_77_ = checkX > startX ? checkX : startX;
            for(int i_78_ = i_69_ < i_71_ ? i_69_ : i_71_; i_77_ < i_78_; i_77_++) {
                if(0 == ((get(i_77_, i_72_ - 1)) & 0x2))
                    return true;
            }
        } else if(i_70_ == startY && 0 == (objectDirectionClip & 0x4)) {
            int i_79_ = checkX > startX ? checkX : startX;
            for(int i_80_ = i_69_ < i_71_ ? i_69_ : i_71_; i_79_ < i_80_; i_79_++) {
                if(((get(i_79_, startY)) & 0x20) == 0)
                    return true;
            }
        }
        return false;
    }

    public static boolean withDistance(int fromMapX, int fromMapY, int toMapX, int toMapY, int fromSizeX, int fromSizeY, int targetSizeX, int targetSizeY) {
        if(fromMapX >= toMapX + targetSizeX || toMapX >= fromSizeX + fromMapX)
            return false;
        if(fromMapY >= toMapY + targetSizeY || toMapY >= fromSizeY + fromMapY)
            return false;
        return true;
    }

    /**
     * Jankily and forcibly fixes clipping on select tiles that shouldn't have them.
     * Not ideal but it works.
     * Update as needed
     */
    public static void fixClipping() {
        // Cosmic altar
        clearClipping(2146, 4833, 0, 15, 0);    // East side
        clearClipping(2123, 4833, 0, 16, 0);    // West side
        clearClipping(2142, 4837, 0, 0, 15);    // North side
        clearClipping(2142, 4814, 0, 0, 16);    // South side
        // Body altar
        clearClipping(2523, 4836, 0, 0, 2);     // South side
        clearClipping(2521, 4836, 0, 0, 2);     // Southwest side
        clearClipping(2522, 4838, 0, 0, 0);
        // Corsair cove
        clearClipping(2579, 2842, 0, 0, 0);     // South dock
        // Catherby shore
        clearClipping(2841, 3432, 0, 0, 0);     // South of crossbow tree
        // Grand tree
        clearClipping(2483, 3490, 1, 0, 0);     // Bar area
        clearClipping(2484, 3489, 1, 0, 0);     // Bar area
        clearClipping(2484, 3490, 1, 0, 0);     // Bar area
        // Darkmeyer
        clearClipping(3594, 3385, 0, 0, 0);     // Custom shortcut
        clearClipping(3565, 3380, 0, 0, 0);     // Rocks by clue step
        clearClipping(3565, 3379, 0, 0, 0);     // Rocks by clue step
        clearClipping(3564, 3379, 0, 0, 0);     // Rocks by clue step
        // Lumbridge RFD room
        clearClipping(3208, 3220, 0, 2, 4);     // Old furniture spawns
        addClipping(3208, 3218, 0, 0, 1);       // Behind the counter
        clearClipping(3212, 3221, 0, 1, 1);     // Double doors
        // Tiranwn log balances
        addClipping(2259, 3250, 0, 4, 0);
        addClipping(2197, 3237, 0, 4, 0);
        addClipping(2290, 3233, 0, 0, 5);
        // Varrock south
        clearClipping(3233, 3383, 0, 3, 0);
        clearClipping(3236, 3382, 0, 2, 1);
        // Leela's house
        clearClipping(1785, 3594, 0, 0, 0);
        // Kraken lair
        addClipping(2261, 10016, 0, 1, 0);
        // Civitas dungeon hub
        clearClipping(1689, 3140, 0, 1, 1);
    }

    public static void clearClipping(int x, int y, int z, int lengthX, int lengthY) {
        for (int width = 0; width < lengthX + 1; width++) {
            for (int height = 0; height < lengthY + 1; height++) {
                Tile tile = Tile.get(x + width, y + height, z, true);
                tile.clipping = 0;
            }
        }
    }

    public static void addClipping(int x, int y, int z, int lengthX, int lengthY) {
        for (int width = 0; width < lengthX + 1; width++) {
            for (int height = 0; height < lengthY + 1; height++) {
                Tile tile = Tile.get(x + width, y + height, z, true);
                tile.flagUnmovable();
            }
        }
    }
}
