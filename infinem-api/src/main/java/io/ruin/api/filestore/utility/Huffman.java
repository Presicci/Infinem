package io.ruin.api.filestore.utility;

import io.ruin.api.buffer.InBuffer;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.FileStore;
import io.ruin.api.filestore.IndexFile;

import java.util.Arrays;

public class Huffman {

    private static final byte[] DATA = {22, 22, 22, 22, 22, 22, 21, 22, 22, 20, 22, 22, 22, 21, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
            22, 22, 22, 22, 22, 22, 22, 3, 8, 22, 16, 22, 16, 17, 7, 13, 13, 13, 16, 7, 10, 6, 16, 10, 11, 12, 12, 12, 12, 13, 13, 14, 14,
            11, 14, 19, 15, 17, 8, 11, 9, 10, 10, 10, 10, 11, 10, 9, 7, 12, 11, 10, 10, 9, 10, 10, 12, 10, 9, 8, 12, 12, 9, 14, 8, 12, 17,
            16, 17, 22, 13, 21, 4, 7, 6, 5, 3, 6, 6, 5, 4, 10, 7, 5, 6, 4, 4, 6, 10, 5, 4, 4, 5, 7, 6, 10, 6, 10, 22, 19, 22, 14, 22, 22,
            22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
            22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
            22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22,
            22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 21, 22, 21, 22, 22, 22, 21, 22, 22
    };

    public static void printData(FileStore fileStore) {
        IndexFile index = fileStore.get(10);
        int archiveId = index.getArchiveId("huffman");
        int fileId = index.getFileId("");
        byte[] data = index.getFile(archiveId, fileId);
        System.out.println(Arrays.toString(data).replace("[", "").replace("]", ""));
    }

    /**
     * Huffman
     */

    private static final Huffman instance = new Huffman(DATA);

    byte[] bits;
    int[] masks;
    int[] keys;

    public Huffman(byte[] var1) {
        int var2 = var1.length;
        this.masks = new int[var2];
        this.bits = var1;
        int[] var3 = new int[33];
        this.keys = new int[8];
        int var4 = 0;

        for (int var5 = 0; var5 < var2; ++var5) {
            byte var6 = var1[var5];
            if (var6 != 0) {
                int var7 = 1 << 32 - var6;
                int var8 = var3[var6];
                this.masks[var5] = var8;
                int var9;
                int var10;
                int var11;
                int var12;
                if ((var8 & var7) != 0) {
                    var9 = var3[var6 - 1];
                } else {
                    var9 = var8 | var7;

                    for (var10 = var6 - 1; var10 >= 1; --var10) {
                        var11 = var3[var10];
                        if (var8 != var11) {
                            break;
                        }

                        var12 = 1 << 32 - var10;
                        if ((var11 & var12) != 0) {
                            var3[var10] = var3[var10 - 1];
                            break;
                        }

                        var3[var10] = var11 | var12;
                    }
                }

                var3[var6] = var9;

                for (var10 = var6 + 1; var10 <= 32; ++var10) {
                    if (var8 == var3[var10]) {
                        var3[var10] = var9;
                    }
                }

                var10 = 0;

                for (var11 = 0; var11 < var6; ++var11) {
                    var12 = Integer.MIN_VALUE >>> var11;
                    if ((var8 & var12) != 0) {
                        if (this.keys[var10] == 0) {
                            this.keys[var10] = var4;
                        }

                        var10 = this.keys[var10];
                    } else {
                        ++var10;
                    }

                    if (var10 >= this.keys.length) {
                        int[] var13 = new int[this.keys.length * 2];

                        for (int var14 = 0; var14 < this.keys.length; ++var14) {
                            var13[var14] = this.keys[var14];
                        }

                        this.keys = var13;
                    }

                    var12 >>>= 1;
                }

                this.keys[var10] = ~var5;
                if (var10 >= var4) {
                    var4 = var10 + 1;
                }
            }
        }

    }

    int compress(byte[] charArray, int index, int charArraySize, byte[] outputArray, int offset) {
        int var6 = 0;
        int var7 = offset << 3;
        for(charArraySize += index; index < charArraySize; ++index) {
            int c = charArray[index] & 255;
            int var9 = this.masks[c];
            byte var10 = this.bits[c];
            if(var10 == 0) {
                throw new RuntimeException("" + c);
            }
            int var11 = var7 >> 3;
            int var12 = var7 & 7;
            var6 &= -var12 >> 31;
            int var13 = (var10 + var12 - 1 >> 3) + var11;
            var12 += 24;
            outputArray[var11] = (byte)(var6 |= var9 >>> var12);
            if(var11 < var13) {
                ++var11;
                var12 -= 8;
                outputArray[var11] = (byte)(var6 = var9 >>> var12);
                if(var11 < var13) {
                    ++var11;
                    var12 -= 8;
                    outputArray[var11] = (byte)(var6 = var9 >>> var12);
                    if(var11 < var13) {
                        ++var11;
                        var12 -= 8;
                        outputArray[var11] = (byte)(var6 = var9 >>> var12);
                        if(var11 < var13) {
                            ++var11;
                            var12 -= 8;
                            outputArray[var11] = (byte)(var6 = var9 << -var12);
                        }
                    }
                }
            }
            var7 += var10;
        }
        return (var7 + 7 >> 3) - offset;
    }

    int decompress(byte[] var1, int var2, byte[] var3, int var4, int var5) {
        if (var5 == 0) {
            return 0;
        } else {
            int var6 = 0;
            var5 += var4;
            int var7 = var2;
            while (true) {
                byte var8 = var1[var7];
                if (var8 < 0) {
                    var6 = this.keys[var6];
                } else {
                    ++var6;
                }
                int var9;
                if ((var9 = this.keys[var6]) < 0) {
                    var3[var4++] = (byte)(~var9);
                    if (var4 >= var5) {
                        break;
                    }
                    var6 = 0;
                }
                if ((var8 & 64) != 0) {
                    var6 = this.keys[var6];
                } else {
                    ++var6;
                }
                if ((var9 = this.keys[var6]) < 0) {
                    var3[var4++] = (byte)(~var9);
                    if (var4 >= var5) {
                        break;
                    }
                    var6 = 0;
                }
                if ((var8 & 32) != 0) {
                    var6 = this.keys[var6];
                } else {
                    ++var6;
                }
                if ((var9 = this.keys[var6]) < 0) {
                    var3[var4++] = (byte)(~var9);
                    if (var4 >= var5) {
                        break;
                    }
                    var6 = 0;
                }
                if ((var8 & 16) != 0) {
                    var6 = this.keys[var6];
                } else {
                    ++var6;
                }
                if ((var9 = this.keys[var6]) < 0) {
                    var3[var4++] = (byte)(~var9);
                    if (var4 >= var5) {
                        break;
                    }
                    var6 = 0;
                }
                if ((var8 & 8) != 0) {
                    var6 = this.keys[var6];
                } else {
                    ++var6;
                }
                if ((var9 = this.keys[var6]) < 0) {
                    var3[var4++] = (byte)(~var9);
                    if (var4 >= var5) {
                        break;
                    }
                    var6 = 0;
                }
                if ((var8 & 4) != 0) {
                    var6 = this.keys[var6];
                } else {
                    ++var6;
                }
                if ((var9 = this.keys[var6]) < 0) {
                    var3[var4++] = (byte)(~var9);
                    if (var4 >= var5) {
                        break;
                    }
                    var6 = 0;
                }
                if ((var8 & 2) != 0) {
                    var6 = this.keys[var6];
                } else {
                    ++var6;
                }
                if ((var9 = this.keys[var6]) < 0) {
                    var3[var4++] = (byte)(~var9);
                    if (var4 >= var5) {
                        break;
                    }
                    var6 = 0;
                }
                if ((var8 & 1) != 0) {
                    var6 = this.keys[var6];
                } else {
                    ++var6;
                }
                if ((var9 = this.keys[var6]) < 0) {
                    var3[var4++] = (byte)(~var9);
                    if (var4 >= var5) {
                        break;
                    }
                    var6 = 0;
                }
                ++var7;
            }
            return var7 + 1 - var2;
        }
    }

    /**
     * Static utils
     */

    static char[] cp1252AsciiExtension = new char[]{'€', '\u0000', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', '\u0000', 'Ž', '\u0000', '\u0000', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '\u0000', 'ž', 'Ÿ'};

    public static byte[] encrypt(String string) {
        int size = string.length();
        byte[] array = new byte[size];
        for (int index = 0; index < size; ++index) {
            char c = string.charAt(index);
            if (c > 0 && c < 128 || c >= 160 && c <= 255) {
                array[index] = (byte)c;
            } else if (c == 8364) {
                array[index] = -128;
            } else if (c == 8218) {
                array[index] = -126;
            } else if (c == 402) {
                array[index] = -125;
            } else if (c == 8222) {
                array[index] = -124;
            } else if (c == 8230) {
                array[index] = -123;
            } else if (c == 8224) {
                array[index] = -122;
            } else if (c == 8225) {
                array[index] = -121;
            } else if (c == 710) {
                array[index] = -120;
            } else if (c == 8240) {
                array[index] = -119;
            } else if (c == 352) {
                array[index] = -118;
            } else if (c == 8249) {
                array[index] = -117;
            } else if (c == 338) {
                array[index] = -116;
            } else if (c == 381) {
                array[index] = -114;
            } else if (c == 8216) {
                array[index] = -111;
            } else if (c == 8217) {
                array[index] = -110;
            } else if (c == 8220) {
                array[index] = -109;
            } else if (c == 8221) {
                array[index] = -108;
            } else if (c == 8226) {
                array[index] = -107;
            } else if (c == 8211) {
                array[index] = -106;
            } else if (c == 8212) {
                array[index] = -105;
            } else if (c == 732) {
                array[index] = -104;
            } else if (c == 8482) {
                array[index] = -103;
            } else if (c == 353) {
                array[index] = -102;
            } else if (c == 8250) {
                array[index] = -101;
            } else if (c == 339) {
                array[index] = -100;
            } else if (c == 382) {
                array[index] = -98;
            } else if (c == 376) {
                array[index] = -97;
            } else {
                array[index] = 63;
            }
        }
        return array;
    }

    public static String decompress(byte[] compressed, int length) {
        byte[] decompressed = new byte[length];

        instance.decompress(compressed, 0, decompressed, 0, length);

        return decodeStringCp1252(decompressed, 0, length);
    }

    public static byte[] compressString(String string) {
        OutBuffer out = new OutBuffer(255);
        byte[] array = encrypt(string);
        out.addSmart(array.length);
        out.resizeIfNeeded(out.position() + array.length * 2);
        int length = Huffman.compressString(array, 0, array.length, out.payload(), out.position());
        out.skip(length);
        return out.toByteArray();
    }

    public static int compressString(byte[] charArray, int startingIndex, int charArraySize, byte[] outputArray, int offset) {
        return instance.compress(charArray, startingIndex, charArraySize, outputArray, offset);
    }

    public static String decodeStringCp1252(byte[] var0, int var1, int var2) {
        char[] var3 = new char[var2];
        int var4 = 0;

        for (int var5 = 0; var5 < var2; ++var5) {
            int var6 = var0[var5 + var1] & 255;
            if (var6 != 0) {
                if (var6 >= 128 && var6 < 160) {
                    char var7 = cp1252AsciiExtension[var6 - 128];
                    if (var7 == 0) {
                        var7 = '?';
                    }

                    var6 = var7;
                }

                var3[var4++] = (char)var6;
            }
        }
        return new String(var3, 0, var4);
    }
}
