package net.runelite.standalone;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSModelData;

@ObfuscatedName("dw")
public class ModelData extends Entity implements RSModelData {
   @ObfuscatedName("ad")
   static int field1641;
   @ObfuscatedName("ai")
   static int[] ModelData_cosine;
   @ObfuscatedName("al")
   static int[] field1640;
   @ObfuscatedName("an")
   static int[] ModelData_sine;
   @ObfuscatedName("ao")
   static int[] field1639;
   @ObfuscatedName("n")
   int[] verticesX;
   @ObfuscatedName("o")
   short[] faceColors;
   @ObfuscatedName("p")
   int[] indices1;
   @ObfuscatedName("q")
   int[] indices2;
   @ObfuscatedName("r")
   int faceCount;
   @ObfuscatedName("s")
   int[] vertexSkins;
   @ObfuscatedName("t")
   byte[] textureRenderTypes;
   @ObfuscatedName("u")
   int[] verticesZ;
   @ObfuscatedName("v")
   int[] verticesY;
   @ObfuscatedName("w")
   int textureTriangleCount;
   @ObfuscatedName("x")
   short[] texTriangleY;
   @ObfuscatedName("y")
   byte[] faceRenderTypes;
   @ObfuscatedName("z")
   int verticesCount;
   @ObfuscatedName("a")
   short[] faceTextures;
   @ObfuscatedName("aa")
   boolean isBoundsCalculated;
   @ObfuscatedName("ab")
   int field1604;
   @ObfuscatedName("ac")
   @ObfuscatedSignature(
           signature = "[Ldp;"
   )
   VertexNormal[] field1636;
   @ObfuscatedName("ap")
   int field1634;
   @ObfuscatedName("ar")
   int field1635;
   @ObfuscatedName("as")
   int field1642;
   @ObfuscatedName("aw")
   public short contrast;
   @ObfuscatedName("ax")
   int field1637;
   @ObfuscatedName("az")
   public short ambient;
   @ObfuscatedName("b")
   byte[] textureCoords;
   @ObfuscatedName("c")
   byte[] faceAlphas;
   public float[][] faceTextureUCoordinates;
   public float[][] faceTextureVCoordinates;
   @ObfuscatedName("d")
   int[][] faceLabelsAlpha;
   @ObfuscatedName("e")
   byte priority;
   @ObfuscatedName("f")
   int[] faceSkins;
   @ObfuscatedName("g")
   short[] texTriangleX;
   @ObfuscatedName("h")
   short[] texTriangleZ;
   @ObfuscatedName("i")
   byte[] faceRenderPriorities;
   @ObfuscatedName("j")
   int[][] vertexLabels;
   @ObfuscatedName("k")
   @ObfuscatedSignature(
           signature = "[Ldp;"
   )
   VertexNormal[] vertexNormals;
   @ObfuscatedName("l")
   @ObfuscatedSignature(
           signature = "[Lee;"
   )
   FaceNormal[] faceNormals;
   @ObfuscatedName("m")
   int[] indices3;

   static {
      field1639 = new int[10000];
      field1640 = new int[10000];
      field1641 = 0;
      ModelData_sine = Rasterizer3D.Rasterizer3D_sine;
      ModelData_cosine = Rasterizer3D.Rasterizer3D_cosine;
   }

   private int[][] field2180;
   private int[][] field2181;
   ModelData() {
      this.verticesCount = 0;
      this.faceCount = 0;
      this.priority = 0;
      this.isBoundsCalculated = false;
   }

   @ObfuscatedSignature(
           signature = "([Ldw;I)V"
   )
   public ModelData(ModelData[] var1, int var2) {
      this.verticesCount = 0;
      this.faceCount = 0;
      this.priority = 0;
      this.isBoundsCalculated = false;
      boolean var3 = false; // L: 921
      boolean var4 = false; // L: 922
      boolean var5 = false; // L: 923
      boolean var6 = false; // L: 924
      boolean var7 = false; // L: 925
      boolean var8 = false; // L: 926
      boolean var9 = false; // L: 927
      this.verticesCount = 0; // L: 928
      this.faceCount = 0; // L: 929
      this.textureTriangleCount = 0; // L: 930
      this.priority = -1; // L: 931

      int var10;
      ModelData var11;
      for (var10 = 0; var10 < var2; ++var10) { // L: 932
         var11 = var1[var10]; // L: 933
         if (var11 != null) { // L: 934
            this.verticesCount += var11.verticesCount; // L: 935
            this.faceCount += var11.faceCount; // L: 936
            this.textureTriangleCount += var11.textureTriangleCount; // L: 937
            if (var11.faceRenderPriorities != null) { // L: 938
               var4 = true;
            } else {
               if (this.priority == -1) { // L: 940
                  this.priority = var11.priority;
               }

               if (this.priority != var11.priority) { // L: 941
                  var4 = true;
               }
            }

            var3 |= var11.faceRenderTypes != null; // L: 943
            var5 |= var11.faceAlphas != null; // L: 944
            var6 |= var11.faceSkins != null; // L: 945
            var7 |= var11.faceTextures != null; // L: 946
            var8 |= var11.textureCoords != null; // L: 947
            var9 |= var11.field2180 != null; // L: 948
         }
      }

      this.verticesX = new int[this.verticesCount]; // L: 951
      this.verticesY = new int[this.verticesCount]; // L: 952
      this.verticesZ = new int[this.verticesCount]; // L: 953
      this.vertexSkins = new int[this.verticesCount]; // L: 954
      this.indices1 = new int[this.faceCount]; // L: 955
      this.indices2 = new int[this.faceCount]; // L: 956
      this.indices3 = new int[this.faceCount]; // L: 957
      if (var3) { // L: 958
         this.faceRenderTypes = new byte[this.faceCount];
      }

      if (var4) { // L: 959
         this.faceRenderPriorities = new byte[this.faceCount];
      }

      if (var5) { // L: 960
         this.faceAlphas = new byte[this.faceCount];
      }

      if (var6) { // L: 961
         this.faceSkins = new int[this.faceCount];
      }

      if (var7) { // L: 962
         this.faceTextures = new short[this.faceCount];
      }

      if (var8) { // L: 963
         this.textureCoords = new byte[this.faceCount];
      }

      if (var9) { // L: 964
         this.field2180 = new int[this.verticesCount][]; // L: 965
         this.field2181 = new int[this.verticesCount][]; // L: 966
      }

      this.faceColors = new short[this.faceCount]; // L: 968
      if (this.textureTriangleCount > 0) { // L: 969
         this.textureRenderTypes = new byte[this.textureTriangleCount]; // L: 970
         this.texTriangleX = new short[this.textureTriangleCount]; // L: 971
         this.texTriangleY = new short[this.textureTriangleCount]; // L: 972
         this.texTriangleZ = new short[this.textureTriangleCount]; // L: 973
      }

      this.verticesCount = 0; // L: 975
      this.faceCount = 0; // L: 976
      this.textureTriangleCount = 0; // L: 977

      for (var10 = 0; var10 < var2; ++var10) { // L: 978
         var11 = var1[var10]; // L: 979
         if (var11 != null) { // L: 980
            int var12;
            for (var12 = 0; var12 < var11.faceCount; ++var12) { // L: 981
               if (var3 && var11.faceRenderTypes != null) { // L: 982
                  this.faceRenderTypes[this.faceCount] = var11.faceRenderTypes[var12]; // L: 983
               }

               if (var4) { // L: 985
                  if (var11.faceRenderPriorities != null) { // L: 986
                     this.faceRenderPriorities[this.faceCount] = var11.faceRenderPriorities[var12];
                  } else {
                     this.faceRenderPriorities[this.faceCount] = var11.priority; // L: 987
                  }
               }

               if (var5 && var11.faceAlphas != null) { // L: 989 990
                  this.faceAlphas[this.faceCount] = var11.faceAlphas[var12];
               }

               if (var6 && var11.faceSkins != null) { // L: 992 993
                  this.faceSkins[this.faceCount] = var11.faceSkins[var12];
               }

               if (var7) { // L: 995
                  if (var11.faceTextures != null) { // L: 996
                     this.faceTextures[this.faceCount] = var11.faceTextures[var12];
                  } else {
                     this.faceTextures[this.faceCount] = -1; // L: 997
                  }
               }

               if (var8) { // L: 999
                  if (var11.textureCoords != null && var11.textureCoords[var12] != -1) { // L: 1000
                     this.textureCoords[this.faceCount] = (byte)(this.textureTriangleCount + var11.textureCoords[var12]);
                  } else {
                     this.textureCoords[this.faceCount] = -1; // L: 1001
                  }
               }

               this.faceColors[this.faceCount] = var11.faceColors[var12]; // L: 1003
               this.indices1[this.faceCount] = this.method2761(var11, var11.indices1[var12]); // L: 1004
               this.indices2[this.faceCount] = this.method2761(var11, var11.indices2[var12]); // L: 1005
               this.indices3[this.faceCount] = this.method2761(var11, var11.indices3[var12]); // L: 1006
               ++this.faceCount; // L: 1007
            }

            for (var12 = 0; var12 < var11.textureTriangleCount; ++var12) { // L: 1009
               byte var13 = this.textureRenderTypes[this.textureTriangleCount] = var11.textureRenderTypes[var12]; // L: 1010
               if (var13 == 0) { // L: 1011
                  this.texTriangleX[this.textureTriangleCount] = (short)this.method2761(var11, var11.texTriangleX[var12]); // L: 1012
                  this.texTriangleY[this.textureTriangleCount] = (short)this.method2761(var11, var11.texTriangleY[var12]); // L: 1013
                  this.texTriangleZ[this.textureTriangleCount] = (short)this.method2761(var11, var11.texTriangleZ[var12]); // L: 1014
               }

               ++this.textureTriangleCount; // L: 1016
            }
         }
      }

   }
   ModelData(byte[] var1) {
      this.verticesCount = 0;
      this.faceCount = 0;
      this.priority = 0;
      this.isBoundsCalculated = false;
      Buffer var2 = new Buffer(10); // L: 64
      var2.writeShort(-2); // L: 65
      if (var1[var1.length - 1] == -3 && var1[var1.length - 2] == -1) { // L: 66
         this.method3700(var1);
      } else if (var1[var1.length - 1] == -2 && var1[var1.length - 2] == -1) { // L: 67
         this.method3753(var1);
      } else if (var1[var1.length - 1] == -1 && var1[var1.length - 2] == -1) { // L: 68
         this.method3702(var1);
      } else {
         this.method3703(var1); // L: 69
      }

   }
   void method3700(byte[] var1) {
      Buffer var2 = new Buffer(var1); // L: 73
      Buffer var3 = new Buffer(var1); // L: 74
      Buffer var4 = new Buffer(var1); // L: 75
      Buffer var5 = new Buffer(var1); // L: 76
      Buffer var6 = new Buffer(var1); // L: 77
      Buffer var7 = new Buffer(var1); // L: 78
      Buffer var8 = new Buffer(var1); // L: 79
      var2.offset = var1.length - 26; // L: 80
      int var9 = var2.readUnsignedShort(); // L: 81
      int var10 = var2.readUnsignedShort(); // L: 82
      int var11 = var2.readUnsignedByte(); // L: 83
      int var12 = var2.readUnsignedByte(); // L: 84
      int var13 = var2.readUnsignedByte(); // L: 85
      int var14 = var2.readUnsignedByte(); // L: 86
      int var15 = var2.readUnsignedByte(); // L: 87
      int var16 = var2.readUnsignedByte(); // L: 88
      int var17 = var2.readUnsignedByte(); // L: 89
      int var18 = var2.readUnsignedByte(); // L: 90
      int var19 = var2.readUnsignedShort(); // L: 91
      int var20 = var2.readUnsignedShort(); // L: 92
      int var21 = var2.readUnsignedShort(); // L: 93
      int var22 = var2.readUnsignedShort(); // L: 94
      int var23 = var2.readUnsignedShort(); // L: 95
      int var24 = var2.readUnsignedShort(); // L: 96
      int var25 = 0; // L: 97
      int var26 = 0; // L: 98
      int var27 = 0; // L: 99
      int var28;
      if (var11 > 0) { // L: 100
         this.textureRenderTypes = new byte[var11]; // L: 101
         var2.offset = 0; // L: 102

         for (var28 = 0; var28 < var11; ++var28) { // L: 103
            byte var29 = this.textureRenderTypes[var28] = var2.readByte(); // L: 104
            if (var29 == 0) { // L: 105
               ++var25;
            }

            if (var29 >= 1 && var29 <= 3) { // L: 106
               ++var26;
            }

            if (var29 == 2) { // L: 107
               ++var27;
            }
         }
      }

      var28 = var11 + var9; // L: 112
      int var30 = var28; // L: 113
      if (var12 == 1) { // L: 114
         var28 += var10;
      }

      int var31 = var28; // L: 115
      var28 += var10; // L: 116
      int var32 = var28; // L: 117
      if (var13 == 255) { // L: 118
         var28 += var10;
      }

      int var33 = var28; // L: 119
      if (var15 == 1) { // L: 120
         var28 += var10;
      }

      int var34 = var28; // L: 121
      var28 += var24; // L: 122
      int var35 = var28; // L: 123
      if (var14 == 1) { // L: 124
         var28 += var10;
      }

      int var36 = var28; // L: 125
      var28 += var22; // L: 126
      int var37 = var28; // L: 127
      if (var16 == 1) { // L: 128
         var28 += var10 * 2;
      }

      int var38 = var28; // L: 129
      var28 += var23; // L: 130
      int var39 = var28; // L: 131
      var28 += var10 * 2; // L: 132
      int var40 = var28; // L: 133
      var28 += var19; // L: 134
      int var41 = var28; // L: 135
      var28 += var20; // L: 136
      int var42 = var28; // L: 137
      var28 += var21; // L: 138
      int var43 = var28; // L: 139
      var28 += var25 * 6; // L: 140
      int var44 = var28; // L: 141
      var28 += var26 * 6; // L: 142
      int var45 = var28; // L: 143
      var28 += var26 * 6; // L: 144
      int var46 = var28; // L: 145
      var28 += var26 * 2; // L: 146
      int var47 = var28; // L: 147
      var28 += var26; // L: 148
      int var48 = var28; // L: 149
      var28 += var26 * 2 + var27 * 2; // L: 150
      this.verticesCount = var9; // L: 152
      this.faceCount = var10; // L: 153
      this.textureTriangleCount = var11; // L: 154
      this.verticesX = new int[var9]; // L: 155
      this.verticesY = new int[var9]; // L: 156
      this.verticesZ = new int[var9]; // L: 157
      this.indices1 = new int[var10]; // L: 158
      this.indices2 = new int[var10]; // L: 159
      this.indices3 = new int[var10]; // L: 160
      if (var17 == 1) { // L: 161
         this.vertexSkins = new int[var9];
      }

      if (var12 == 1) { // L: 162
         this.faceRenderTypes = new byte[var10];
      }

      if (var13 == 255) { // L: 163
         this.faceRenderPriorities = new byte[var10];
      } else {
         this.priority = (byte)var13; // L: 164
      }

      if (var14 == 1) { // L: 165
         this.faceAlphas = new byte[var10];
      }

      if (var15 == 1) { // L: 166
         this.faceSkins = new int[var10];
      }

      if (var16 == 1) { // L: 167
         this.faceTextures = new short[var10];
      }

      if (var16 == 1 && var11 > 0) { // L: 168
         this.textureCoords = new byte[var10];
      }

      if (var18 == 1) { // L: 169
         this.field2180 = new int[var9][]; // L: 170
         this.field2181 = new int[var9][]; // L: 171
      }

      this.faceColors = new short[var10]; // L: 173
      if (var11 > 0) { // L: 174
         this.texTriangleX = new short[var11]; // L: 175
         this.texTriangleY = new short[var11]; // L: 176
         this.texTriangleZ = new short[var11]; // L: 177
      }

      var2.offset = var11; // L: 179
      var3.offset = var40; // L: 180
      var4.offset = var41; // L: 181
      var5.offset = var42; // L: 182
      var6.offset = var34; // L: 183
      int var50 = 0; // L: 184
      int var51 = 0; // L: 185
      int var52 = 0; // L: 186

      int var53;
      int var54;
      int var55;
      int var56;
      int var57;
      for (var53 = 0; var53 < var9; ++var53) { // L: 187
         var54 = var2.readUnsignedByte(); // L: 188
         var55 = 0; // L: 189
         if ((var54 & 1) != 0) { // L: 190
            var55 = var3.readShortSmart();
         }

         var56 = 0; // L: 191
         if ((var54 & 2) != 0) { // L: 192
            var56 = var4.readShortSmart();
         }

         var57 = 0; // L: 193
         if ((var54 & 4) != 0) { // L: 194
            var57 = var5.readShortSmart();
         }

         this.verticesX[var53] = var50 + var55; // L: 195
         this.verticesY[var53] = var51 + var56; // L: 196
         this.verticesZ[var53] = var52 + var57; // L: 197
         var50 = this.verticesX[var53]; // L: 198
         var51 = this.verticesY[var53]; // L: 199
         var52 = this.verticesZ[var53]; // L: 200
         if (var17 == 1) { // L: 201
            this.vertexSkins[var53] = var6.readUnsignedByte();
         }
      }

      if (var18 == 1) { // L: 203
         for (var53 = 0; var53 < var9; ++var53) { // L: 204
            var54 = var6.readUnsignedByte(); // L: 205
            this.field2180[var53] = new int[var54]; // L: 206
            this.field2181[var53] = new int[var54]; // L: 207

            for (var55 = 0; var55 < var54; ++var55) { // L: 208
               this.field2180[var53][var55] = var6.readUnsignedByte(); // L: 209
               this.field2181[var53][var55] = var6.readUnsignedByte(); // L: 210
            }
         }
      }

      var2.offset = var39; // L: 214
      var3.offset = var30; // L: 215
      var4.offset = var32; // L: 216
      var5.offset = var35; // L: 217
      var6.offset = var33; // L: 218
      var7.offset = var37; // L: 219
      var8.offset = var38; // L: 220

      for (var53 = 0; var53 < var10; ++var53) { // L: 221
         this.faceColors[var53] = (short)var2.readUnsignedShort(); // L: 222
         if (var12 == 1) { // L: 223
            this.faceRenderTypes[var53] = var3.readByte();
         }

         if (var13 == 255) { // L: 224
            this.faceRenderPriorities[var53] = var4.readByte();
         }

         if (var14 == 1) { // L: 225
            this.faceAlphas[var53] = var5.readByte();
         }

         if (var15 == 1) { // L: 226
            this.faceSkins[var53] = var6.readUnsignedByte();
         }

         if (var16 == 1) { // L: 227
            this.faceTextures[var53] = (short)(var7.readUnsignedShort() - 1);
         }

         if (this.textureCoords != null && this.faceTextures[var53] != -1) { // L: 228
            this.textureCoords[var53] = (byte)(var8.readUnsignedByte() - 1);
         }
      }

      var2.offset = var36; // L: 230
      var3.offset = var31; // L: 231
      var53 = 0; // L: 232
      var54 = 0; // L: 233
      var55 = 0; // L: 234
      var56 = 0; // L: 235

      int var58;
      for (var57 = 0; var57 < var10; ++var57) { // L: 236
         var58 = var3.readUnsignedByte(); // L: 237
         if (var58 == 1) { // L: 238
            var53 = var2.readShortSmart() + var56; // L: 239
            var54 = var2.readShortSmart() + var53; // L: 241
            var55 = var2.readShortSmart() + var54; // L: 243
            var56 = var55; // L: 244
            this.indices1[var57] = var53; // L: 245
            this.indices2[var57] = var54; // L: 246
            this.indices3[var57] = var55; // L: 247
         }

         if (var58 == 2) { // L: 249
            var54 = var55; // L: 250
            var55 = var2.readShortSmart() + var56; // L: 251
            var56 = var55; // L: 252
            this.indices1[var57] = var53; // L: 253
            this.indices2[var57] = var54; // L: 254
            this.indices3[var57] = var55; // L: 255
         }

         if (var58 == 3) { // L: 257
            var53 = var55; // L: 258
            var55 = var2.readShortSmart() + var56; // L: 259
            var56 = var55; // L: 260
            this.indices1[var57] = var53; // L: 261
            this.indices2[var57] = var54; // L: 262
            this.indices3[var57] = var55; // L: 263
         }

         if (var58 == 4) { // L: 265
            int var59 = var53; // L: 266
            var53 = var54; // L: 267
            var54 = var59; // L: 268
            var55 = var2.readShortSmart() + var56; // L: 269
            var56 = var55; // L: 270
            this.indices1[var57] = var53; // L: 271
            this.indices2[var57] = var59; // L: 272
            this.indices3[var57] = var55; // L: 273
         }
      }

      var2.offset = var43; // L: 276
      var3.offset = var44; // L: 277
      var4.offset = var45; // L: 278
      var5.offset = var46; // L: 279
      var6.offset = var47; // L: 280
      var7.offset = var48; // L: 281

      for (var57 = 0; var57 < var11; ++var57) { // L: 282
         var58 = this.textureRenderTypes[var57] & 255; // L: 283
         if (var58 == 0) { // L: 284
            this.texTriangleX[var57] = (short)var2.readUnsignedShort(); // L: 285
            this.texTriangleY[var57] = (short)var2.readUnsignedShort(); // L: 286
            this.texTriangleZ[var57] = (short)var2.readUnsignedShort(); // L: 287
         }
      }

      var2.offset = var28; // L: 290
      var57 = var2.readUnsignedByte(); // L: 291
      if (var57 != 0) { // L: 292
         new ModelData0();
         var2.readUnsignedShort(); // L: 294
         var2.readUnsignedShort(); // L: 295
         var2.readUnsignedShort(); // L: 296
         var2.readInt(); // L: 297
      }

   } // L: 299

   @ObfuscatedName("e")
   void method3753(byte[] var1) {
      boolean var2 = false; // L: 302
      boolean var3 = false; // L: 303
      Buffer var4 = new Buffer(var1); // L: 304
      Buffer var5 = new Buffer(var1); // L: 305
      Buffer var6 = new Buffer(var1); // L: 306
      Buffer var7 = new Buffer(var1); // L: 307
      Buffer var8 = new Buffer(var1); // L: 308
      var4.offset = var1.length - 23; // L: 309
      int var9 = var4.readUnsignedShort(); // L: 310
      int var10 = var4.readUnsignedShort(); // L: 311
      int var11 = var4.readUnsignedByte(); // L: 312
      int var12 = var4.readUnsignedByte(); // L: 313
      int var13 = var4.readUnsignedByte(); // L: 314
      int var14 = var4.readUnsignedByte(); // L: 315
      int var15 = var4.readUnsignedByte(); // L: 316
      int var16 = var4.readUnsignedByte(); // L: 317
      int var17 = var4.readUnsignedByte(); // L: 318
      int var18 = var4.readUnsignedShort(); // L: 319
      int var19 = var4.readUnsignedShort(); // L: 320
      int var20 = var4.readUnsignedShort(); // L: 321
      int var21 = var4.readUnsignedShort(); // L: 322
      int var22 = var4.readUnsignedShort(); // L: 323
      byte var23 = 0; // L: 324
      int var47 = var23 + var9; // L: 326
      int var25 = var47; // L: 327
      var47 += var10; // L: 328
      int var26 = var47; // L: 329
      if (var13 == 255) { // L: 330
         var47 += var10;
      }

      int var27 = var47; // L: 331
      if (var15 == 1) { // L: 332
         var47 += var10;
      }

      int var28 = var47; // L: 333
      if (var12 == 1) { // L: 334
         var47 += var10;
      }

      int var29 = var47; // L: 335
      var47 += var22; // L: 336
      int var30 = var47; // L: 337
      if (var14 == 1) { // L: 338
         var47 += var10;
      }

      int var31 = var47; // L: 339
      var47 += var21; // L: 340
      int var32 = var47; // L: 341
      var47 += var10 * 2; // L: 342
      int var33 = var47; // L: 343
      var47 += var11 * 6; // L: 344
      int var34 = var47; // L: 345
      var47 += var18; // L: 346
      int var35 = var47; // L: 347
      var47 += var19; // L: 348
      int var10000 = var47 + var20; // L: 350
      this.verticesCount = var9; // L: 351
      this.faceCount = var10; // L: 352
      this.textureTriangleCount = var11; // L: 353
      this.verticesX = new int[var9]; // L: 354
      this.verticesY = new int[var9]; // L: 355
      this.verticesZ = new int[var9]; // L: 356
      this.indices1 = new int[var10]; // L: 357
      this.indices2 = new int[var10]; // L: 358
      this.indices3 = new int[var10]; // L: 359
      if (var11 > 0) { // L: 360
         this.textureRenderTypes = new byte[var11]; // L: 361
         this.texTriangleX = new short[var11]; // L: 362
         this.texTriangleY = new short[var11]; // L: 363
         this.texTriangleZ = new short[var11]; // L: 364
      }

      if (var16 == 1) { // L: 366
         this.vertexSkins = new int[var9];
      }

      if (var12 == 1) { // L: 367
         this.faceRenderTypes = new byte[var10]; // L: 368
         this.textureCoords = new byte[var10]; // L: 369
         this.faceTextures = new short[var10]; // L: 370
      }

      if (var13 == 255) { // L: 372
         this.faceRenderPriorities = new byte[var10];
      } else {
         this.priority = (byte)var13; // L: 373
      }

      if (var14 == 1) { // L: 374
         this.faceAlphas = new byte[var10];
      }

      if (var15 == 1) { // L: 375
         this.faceSkins = new int[var10];
      }

      if (var17 == 1) { // L: 376
         this.field2180 = new int[var9][]; // L: 377
         this.field2181 = new int[var9][]; // L: 378
      }

      this.faceColors = new short[var10]; // L: 380
      var4.offset = var23; // L: 381
      var5.offset = var34; // L: 382
      var6.offset = var35; // L: 383
      var7.offset = var47; // L: 384
      var8.offset = var29; // L: 385
      int var37 = 0; // L: 386
      int var38 = 0; // L: 387
      int var39 = 0; // L: 388

      int var40;
      int var41;
      int var42;
      int var43;
      int var44;
      for (var40 = 0; var40 < var9; ++var40) { // L: 389
         var41 = var4.readUnsignedByte(); // L: 390
         var42 = 0; // L: 391
         if ((var41 & 1) != 0) { // L: 392
            var42 = var5.readShortSmart();
         }

         var43 = 0; // L: 393
         if ((var41 & 2) != 0) { // L: 394
            var43 = var6.readShortSmart();
         }

         var44 = 0; // L: 395
         if ((var41 & 4) != 0) { // L: 396
            var44 = var7.readShortSmart();
         }

         this.verticesX[var40] = var37 + var42; // L: 397
         this.verticesY[var40] = var38 + var43; // L: 398
         this.verticesZ[var40] = var39 + var44; // L: 399
         var37 = this.verticesX[var40]; // L: 400
         var38 = this.verticesY[var40]; // L: 401
         var39 = this.verticesZ[var40]; // L: 402
         if (var16 == 1) { // L: 403
            this.vertexSkins[var40] = var8.readUnsignedByte();
         }
      }

      if (var17 == 1) { // L: 405
         for (var40 = 0; var40 < var9; ++var40) { // L: 406
            var41 = var8.readUnsignedByte(); // L: 407
            this.field2180[var40] = new int[var41]; // L: 408
            this.field2181[var40] = new int[var41]; // L: 409

            for (var42 = 0; var42 < var41; ++var42) { // L: 410
               this.field2180[var40][var42] = var8.readUnsignedByte(); // L: 411
               this.field2181[var40][var42] = var8.readUnsignedByte(); // L: 412
            }
         }
      }

      var4.offset = var32; // L: 416
      var5.offset = var28; // L: 417
      var6.offset = var26; // L: 418
      var7.offset = var30; // L: 419
      var8.offset = var27; // L: 420

      for (var40 = 0; var40 < var10; ++var40) { // L: 421
         this.faceColors[var40] = (short)var4.readUnsignedShort(); // L: 422
         if (var12 == 1) { // L: 423
            var41 = var5.readUnsignedByte(); // L: 424
            if ((var41 & 1) == 1) { // L: 425
               this.faceRenderTypes[var40] = 1; // L: 426
               var2 = true; // L: 427
            } else {
               this.faceRenderTypes[var40] = 0; // L: 429
            }

            if ((var41 & 2) == 2) { // L: 430
               this.textureCoords[var40] = (byte)(var41 >> 2); // L: 431
               this.faceTextures[var40] = this.faceColors[var40]; // L: 432
               this.faceColors[var40] = 127; // L: 433
               if (this.faceTextures[var40] != -1) { // L: 434
                  var3 = true;
               }
            } else {
               this.textureCoords[var40] = -1; // L: 437
               this.faceTextures[var40] = -1; // L: 438
            }
         }

         if (var13 == 255) { // L: 441
            this.faceRenderPriorities[var40] = var6.readByte();
         }

         if (var14 == 1) { // L: 442
            this.faceAlphas[var40] = var7.readByte();
         }

         if (var15 == 1) { // L: 443
            this.faceSkins[var40] = var8.readUnsignedByte();
         }
      }

      var4.offset = var31; // L: 445
      var5.offset = var25; // L: 446
      var40 = 0; // L: 447
      var41 = 0; // L: 448
      var42 = 0; // L: 449
      var43 = 0; // L: 450

      int var45;
      int var46;
      for (var44 = 0; var44 < var10; ++var44) { // L: 451
         var45 = var5.readUnsignedByte(); // L: 452
         if (var45 == 1) { // L: 453
            var40 = var4.readShortSmart() + var43; // L: 454
            var41 = var4.readShortSmart() + var40; // L: 456
            var42 = var4.readShortSmart() + var41; // L: 458
            var43 = var42; // L: 459
            this.indices1[var44] = var40; // L: 460
            this.indices2[var44] = var41; // L: 461
            this.indices3[var44] = var42; // L: 462
         }

         if (var45 == 2) { // L: 464
            var41 = var42; // L: 465
            var42 = var4.readShortSmart() + var43; // L: 466
            var43 = var42; // L: 467
            this.indices1[var44] = var40; // L: 468
            this.indices2[var44] = var41; // L: 469
            this.indices3[var44] = var42; // L: 470
         }

         if (var45 == 3) { // L: 472
            var40 = var42; // L: 473
            var42 = var4.readShortSmart() + var43; // L: 474
            var43 = var42; // L: 475
            this.indices1[var44] = var40; // L: 476
            this.indices2[var44] = var41; // L: 477
            this.indices3[var44] = var42; // L: 478
         }

         if (var45 == 4) { // L: 480
            var46 = var40; // L: 481
            var40 = var41; // L: 482
            var41 = var46; // L: 483
            var42 = var4.readShortSmart() + var43; // L: 484
            var43 = var42; // L: 485
            this.indices1[var44] = var40; // L: 486
            this.indices2[var44] = var46; // L: 487
            this.indices3[var44] = var42; // L: 488
         }
      }

      var4.offset = var33; // L: 491

      for (var44 = 0; var44 < var11; ++var44) { // L: 492
         this.textureRenderTypes[var44] = 0; // L: 493
         this.texTriangleX[var44] = (short)var4.readUnsignedShort(); // L: 494
         this.texTriangleY[var44] = (short)var4.readUnsignedShort(); // L: 495
         this.texTriangleZ[var44] = (short)var4.readUnsignedShort(); // L: 496
      }

      if (this.textureCoords != null) { // L: 498
         boolean var48 = false; // L: 499

         for (var45 = 0; var45 < var10; ++var45) { // L: 500
            var46 = this.textureCoords[var45] & 255; // L: 501
            if (var46 != 255) { // L: 502
               if (this.indices1[var45] == (this.texTriangleX[var46] & '\uffff') && this.indices2[var45] == (this.texTriangleY[var46] & '\uffff') && this.indices3[var45] == (this.texTriangleZ[var46] & '\uffff')) { // L: 503
                  this.textureCoords[var45] = -1;
               } else {
                  var48 = true; // L: 504
               }
            }
         }

         if (!var48) { // L: 507
            this.textureCoords = null;
         }
      }

      if (!var3) { // L: 509
         this.faceTextures = null;
      }

      if (!var2) { // L: 510
         this.faceRenderTypes = null;
      }

   } // L: 511

   @ObfuscatedName("r")
   void method3702(byte[] var1) {
      Buffer var2 = new Buffer(var1); // L: 514
      Buffer var3 = new Buffer(var1); // L: 515
      Buffer var4 = new Buffer(var1); // L: 516
      Buffer var5 = new Buffer(var1); // L: 517
      Buffer var6 = new Buffer(var1); // L: 518
      Buffer var7 = new Buffer(var1); // L: 519
      Buffer var8 = new Buffer(var1); // L: 520
      var2.offset = var1.length - 23; // L: 521
      int var9 = var2.readUnsignedShort(); // L: 522
      int var10 = var2.readUnsignedShort(); // L: 523
      int var11 = var2.readUnsignedByte(); // L: 524
      int var12 = var2.readUnsignedByte(); // L: 525
      int var13 = var2.readUnsignedByte(); // L: 526
      int var14 = var2.readUnsignedByte(); // L: 527
      int var15 = var2.readUnsignedByte(); // L: 528
      int var16 = var2.readUnsignedByte(); // L: 529
      int var17 = var2.readUnsignedByte(); // L: 530
      int var18 = var2.readUnsignedShort(); // L: 531
      int var19 = var2.readUnsignedShort(); // L: 532
      int var20 = var2.readUnsignedShort(); // L: 533
      int var21 = var2.readUnsignedShort(); // L: 534
      int var22 = var2.readUnsignedShort(); // L: 535
      int var23 = 0; // L: 536
      int var24 = 0; // L: 537
      int var25 = 0; // L: 538
      int var26;
      if (var11 > 0) { // L: 539
         this.textureRenderTypes = new byte[var11]; // L: 540
         var2.offset = 0; // L: 541

         for (var26 = 0; var26 < var11; ++var26) { // L: 542
            byte var27 = this.textureRenderTypes[var26] = var2.readByte(); // L: 543
            if (var27 == 0) { // L: 544
               ++var23;
            }

            if (var27 >= 1 && var27 <= 3) { // L: 545
               ++var24;
            }

            if (var27 == 2) { // L: 546
               ++var25;
            }
         }
      }

      var26 = var11 + var9; // L: 551
      int var28 = var26; // L: 552
      if (var12 == 1) { // L: 553
         var26 += var10;
      }

      int var29 = var26; // L: 554
      var26 += var10; // L: 555
      int var30 = var26; // L: 556
      if (var13 == 255) { // L: 557
         var26 += var10;
      }

      int var31 = var26; // L: 558
      if (var15 == 1) { // L: 559
         var26 += var10;
      }

      int var32 = var26; // L: 560
      if (var17 == 1) { // L: 561
         var26 += var9;
      }

      int var33 = var26; // L: 562
      if (var14 == 1) { // L: 563
         var26 += var10;
      }

      int var34 = var26; // L: 564
      var26 += var21; // L: 565
      int var35 = var26; // L: 566
      if (var16 == 1) { // L: 567
         var26 += var10 * 2;
      }

      int var36 = var26; // L: 568
      var26 += var22; // L: 569
      int var37 = var26; // L: 570
      var26 += var10 * 2; // L: 571
      int var38 = var26; // L: 572
      var26 += var18; // L: 573
      int var39 = var26; // L: 574
      var26 += var19; // L: 575
      int var40 = var26; // L: 576
      var26 += var20; // L: 577
      int var41 = var26; // L: 578
      var26 += var23 * 6; // L: 579
      int var42 = var26; // L: 580
      var26 += var24 * 6; // L: 581
      int var43 = var26; // L: 582
      var26 += var24 * 6; // L: 583
      int var44 = var26; // L: 584
      var26 += var24 * 2; // L: 585
      int var45 = var26; // L: 586
      var26 += var24; // L: 587
      int var46 = var26; // L: 588
      var26 += var24 * 2 + var25 * 2; // L: 589
      this.verticesCount = var9; // L: 591
      this.faceCount = var10; // L: 592
      this.textureTriangleCount = var11; // L: 593
      this.verticesX = new int[var9]; // L: 594
      this.verticesY = new int[var9]; // L: 595
      this.verticesZ = new int[var9]; // L: 596
      this.indices1 = new int[var10]; // L: 597
      this.indices2 = new int[var10]; // L: 598
      this.indices3 = new int[var10]; // L: 599
      if (var17 == 1) { // L: 600
         this.vertexSkins = new int[var9];
      }

      if (var12 == 1) { // L: 601
         this.faceRenderTypes = new byte[var10];
      }

      if (var13 == 255) { // L: 602
         this.faceRenderPriorities = new byte[var10];
      } else {
         this.priority = (byte)var13; // L: 603
      }

      if (var14 == 1) { // L: 604
         this.faceAlphas = new byte[var10];
      }

      if (var15 == 1) { // L: 605
         this.faceSkins = new int[var10];
      }

      if (var16 == 1) { // L: 606
         this.faceTextures = new short[var10];
      }

      if (var16 == 1 && var11 > 0) { // L: 607
         this.textureCoords = new byte[var10];
      }

      this.faceColors = new short[var10]; // L: 608
      if (var11 > 0) { // L: 609
         this.texTriangleX = new short[var11]; // L: 610
         this.texTriangleY = new short[var11]; // L: 611
         this.texTriangleZ = new short[var11]; // L: 612
      }

      var2.offset = var11; // L: 614
      var3.offset = var38; // L: 615
      var4.offset = var39; // L: 616
      var5.offset = var40; // L: 617
      var6.offset = var32; // L: 618
      int var48 = 0; // L: 619
      int var49 = 0; // L: 620
      int var50 = 0; // L: 621

      int var51;
      int var52;
      int var53;
      int var54;
      int var55;
      for (var51 = 0; var51 < var9; ++var51) { // L: 622
         var52 = var2.readUnsignedByte(); // L: 623
         var53 = 0; // L: 624
         if ((var52 & 1) != 0) { // L: 625
            var53 = var3.readShortSmart();
         }

         var54 = 0; // L: 626
         if ((var52 & 2) != 0) { // L: 627
            var54 = var4.readShortSmart();
         }

         var55 = 0; // L: 628
         if ((var52 & 4) != 0) { // L: 629
            var55 = var5.readShortSmart();
         }

         this.verticesX[var51] = var48 + var53; // L: 630
         this.verticesY[var51] = var49 + var54; // L: 631
         this.verticesZ[var51] = var50 + var55; // L: 632
         var48 = this.verticesX[var51]; // L: 633
         var49 = this.verticesY[var51]; // L: 634
         var50 = this.verticesZ[var51]; // L: 635
         if (var17 == 1) { // L: 636
            this.vertexSkins[var51] = var6.readUnsignedByte();
         }
      }

      var2.offset = var37; // L: 638
      var3.offset = var28; // L: 639
      var4.offset = var30; // L: 640
      var5.offset = var33; // L: 641
      var6.offset = var31; // L: 642
      var7.offset = var35; // L: 643
      var8.offset = var36; // L: 644

      for (var51 = 0; var51 < var10; ++var51) { // L: 645
         this.faceColors[var51] = (short)var2.readUnsignedShort(); // L: 646
         if (var12 == 1) { // L: 647
            this.faceRenderTypes[var51] = var3.readByte();
         }

         if (var13 == 255) { // L: 648
            this.faceRenderPriorities[var51] = var4.readByte();
         }

         if (var14 == 1) { // L: 649
            this.faceAlphas[var51] = var5.readByte();
         }

         if (var15 == 1) { // L: 650
            this.faceSkins[var51] = var6.readUnsignedByte();
         }

         if (var16 == 1) { // L: 651
            this.faceTextures[var51] = (short)(var7.readUnsignedShort() - 1);
         }

         if (this.textureCoords != null && this.faceTextures[var51] != -1) { // L: 652
            this.textureCoords[var51] = (byte)(var8.readUnsignedByte() - 1);
         }
      }

      var2.offset = var34; // L: 654
      var3.offset = var29; // L: 655
      var51 = 0; // L: 656
      var52 = 0; // L: 657
      var53 = 0; // L: 658
      var54 = 0; // L: 659

      int var56;
      for (var55 = 0; var55 < var10; ++var55) { // L: 660
         var56 = var3.readUnsignedByte(); // L: 661
         if (var56 == 1) { // L: 662
            var51 = var2.readShortSmart() + var54; // L: 663
            var52 = var2.readShortSmart() + var51; // L: 665
            var53 = var2.readShortSmart() + var52; // L: 667
            var54 = var53; // L: 668
            this.indices1[var55] = var51; // L: 669
            this.indices2[var55] = var52; // L: 670
            this.indices3[var55] = var53; // L: 671
         }

         if (var56 == 2) { // L: 673
            var52 = var53; // L: 674
            var53 = var2.readShortSmart() + var54; // L: 675
            var54 = var53; // L: 676
            this.indices1[var55] = var51; // L: 677
            this.indices2[var55] = var52; // L: 678
            this.indices3[var55] = var53; // L: 679
         }

         if (var56 == 3) { // L: 681
            var51 = var53; // L: 682
            var53 = var2.readShortSmart() + var54; // L: 683
            var54 = var53; // L: 684
            this.indices1[var55] = var51; // L: 685
            this.indices2[var55] = var52; // L: 686
            this.indices3[var55] = var53; // L: 687
         }

         if (var56 == 4) { // L: 689
            int var57 = var51; // L: 690
            var51 = var52; // L: 691
            var52 = var57; // L: 692
            var53 = var2.readShortSmart() + var54; // L: 693
            var54 = var53; // L: 694
            this.indices1[var55] = var51; // L: 695
            this.indices2[var55] = var57; // L: 696
            this.indices3[var55] = var53; // L: 697
         }
      }

      var2.offset = var41; // L: 700
      var3.offset = var42; // L: 701
      var4.offset = var43; // L: 702
      var5.offset = var44; // L: 703
      var6.offset = var45; // L: 704
      var7.offset = var46; // L: 705

      for (var55 = 0; var55 < var11; ++var55) { // L: 706
         var56 = this.textureRenderTypes[var55] & 255; // L: 707
         if (var56 == 0) { // L: 708
            this.texTriangleX[var55] = (short)var2.readUnsignedShort(); // L: 709
            this.texTriangleY[var55] = (short)var2.readUnsignedShort(); // L: 710
            this.texTriangleZ[var55] = (short)var2.readUnsignedShort(); // L: 711
         }
      }

      var2.offset = var26; // L: 714
      var55 = var2.readUnsignedByte(); // L: 715
      if (var55 != 0) { // L: 716
         new ModelData0();
         var2.readUnsignedShort(); // L: 718
         var2.readUnsignedShort(); // L: 719
         var2.readUnsignedShort(); // L: 720
         var2.readInt(); // L: 721
      }

   } // L: 723

   @ObfuscatedName("o")
   void method3703(byte[] var1) {
      boolean var2 = false; // L: 726
      boolean var3 = false; // L: 727
      Buffer var4 = new Buffer(var1); // L: 728
      Buffer var5 = new Buffer(var1); // L: 729
      Buffer var6 = new Buffer(var1); // L: 730
      Buffer var7 = new Buffer(var1); // L: 731
      Buffer var8 = new Buffer(var1); // L: 732
      var4.offset = var1.length - 18; // L: 733
      int var9 = var4.readUnsignedShort(); // L: 734
      int var10 = var4.readUnsignedShort(); // L: 735
      int var11 = var4.readUnsignedByte(); // L: 736
      int var12 = var4.readUnsignedByte(); // L: 737
      int var13 = var4.readUnsignedByte(); // L: 738
      int var14 = var4.readUnsignedByte(); // L: 739
      int var15 = var4.readUnsignedByte(); // L: 740
      int var16 = var4.readUnsignedByte(); // L: 741
      int var17 = var4.readUnsignedShort(); // L: 742
      int var18 = var4.readUnsignedShort(); // L: 743
      int var19 = var4.readUnsignedShort(); // L: 744
      int var20 = var4.readUnsignedShort(); // L: 745
      byte var21 = 0; // L: 746
      int var45 = var21 + var9; // L: 748
      int var23 = var45; // L: 749
      var45 += var10; // L: 750
      int var24 = var45; // L: 751
      if (var13 == 255) { // L: 752
         var45 += var10;
      }

      int var25 = var45; // L: 753
      if (var15 == 1) { // L: 754
         var45 += var10;
      }

      int var26 = var45; // L: 755
      if (var12 == 1) { // L: 756
         var45 += var10;
      }

      int var27 = var45; // L: 757
      if (var16 == 1) { // L: 758
         var45 += var9;
      }

      int var28 = var45; // L: 759
      if (var14 == 1) { // L: 760
         var45 += var10;
      }

      int var29 = var45; // L: 761
      var45 += var20; // L: 762
      int var30 = var45; // L: 763
      var45 += var10 * 2; // L: 764
      int var31 = var45; // L: 765
      var45 += var11 * 6; // L: 766
      int var32 = var45; // L: 767
      var45 += var17; // L: 768
      int var33 = var45; // L: 769
      var45 += var18; // L: 770
      int var10000 = var45 + var19; // L: 772
      this.verticesCount = var9; // L: 773
      this.faceCount = var10; // L: 774
      this.textureTriangleCount = var11; // L: 775
      this.verticesX = new int[var9]; // L: 776
      this.verticesY = new int[var9]; // L: 777
      this.verticesZ = new int[var9]; // L: 778
      this.indices1 = new int[var10]; // L: 779
      this.indices2 = new int[var10]; // L: 780
      this.indices3 = new int[var10]; // L: 781
      if (var11 > 0) { // L: 782
         this.textureRenderTypes = new byte[var11]; // L: 783
         this.texTriangleX = new short[var11]; // L: 784
         this.texTriangleY = new short[var11]; // L: 785
         this.texTriangleZ = new short[var11]; // L: 786
      }

      if (var16 == 1) { // L: 788
         this.vertexSkins = new int[var9];
      }

      if (var12 == 1) { // L: 789
         this.faceRenderTypes = new byte[var10]; // L: 790
         this.textureCoords = new byte[var10]; // L: 791
         this.faceTextures = new short[var10]; // L: 792
      }

      if (var13 == 255) { // L: 794
         this.faceRenderPriorities = new byte[var10];
      } else {
         this.priority = (byte)var13; // L: 795
      }

      if (var14 == 1) { // L: 796
         this.faceAlphas = new byte[var10];
      }

      if (var15 == 1) { // L: 797
         this.faceSkins = new int[var10];
      }

      this.faceColors = new short[var10]; // L: 798
      var4.offset = var21; // L: 799
      var5.offset = var32; // L: 800
      var6.offset = var33; // L: 801
      var7.offset = var45; // L: 802
      var8.offset = var27; // L: 803
      int var35 = 0; // L: 804
      int var36 = 0; // L: 805
      int var37 = 0; // L: 806

      int var38;
      int var39;
      int var40;
      int var41;
      int var42;
      for (var38 = 0; var38 < var9; ++var38) { // L: 807
         var39 = var4.readUnsignedByte(); // L: 808
         var40 = 0; // L: 809
         if ((var39 & 1) != 0) { // L: 810
            var40 = var5.readShortSmart();
         }

         var41 = 0; // L: 811
         if ((var39 & 2) != 0) { // L: 812
            var41 = var6.readShortSmart();
         }

         var42 = 0; // L: 813
         if ((var39 & 4) != 0) { // L: 814
            var42 = var7.readShortSmart();
         }

         this.verticesX[var38] = var35 + var40; // L: 815
         this.verticesY[var38] = var36 + var41; // L: 816
         this.verticesZ[var38] = var37 + var42; // L: 817
         var35 = this.verticesX[var38]; // L: 818
         var36 = this.verticesY[var38]; // L: 819
         var37 = this.verticesZ[var38]; // L: 820
         if (var16 == 1) { // L: 821
            this.vertexSkins[var38] = var8.readUnsignedByte();
         }
      }

      var4.offset = var30; // L: 823
      var5.offset = var26; // L: 824
      var6.offset = var24; // L: 825
      var7.offset = var28; // L: 826
      var8.offset = var25; // L: 827

      for (var38 = 0; var38 < var10; ++var38) { // L: 828
         this.faceColors[var38] = (short)var4.readUnsignedShort(); // L: 829
         if (var12 == 1) { // L: 830
            var39 = var5.readUnsignedByte(); // L: 831
            if ((var39 & 1) == 1) { // L: 832
               this.faceRenderTypes[var38] = 1; // L: 833
               var2 = true; // L: 834
            } else {
               this.faceRenderTypes[var38] = 0; // L: 836
            }

            if ((var39 & 2) == 2) { // L: 837
               this.textureCoords[var38] = (byte)(var39 >> 2); // L: 838
               this.faceTextures[var38] = this.faceColors[var38]; // L: 839
               this.faceColors[var38] = 127; // L: 840
               if (this.faceTextures[var38] != -1) { // L: 841
                  var3 = true;
               }
            } else {
               this.textureCoords[var38] = -1; // L: 844
               this.faceTextures[var38] = -1; // L: 845
            }
         }

         if (var13 == 255) { // L: 848
            this.faceRenderPriorities[var38] = var6.readByte();
         }

         if (var14 == 1) { // L: 849
            this.faceAlphas[var38] = var7.readByte();
         }

         if (var15 == 1) { // L: 850
            this.faceSkins[var38] = var8.readUnsignedByte();
         }
      }

      var4.offset = var29; // L: 852
      var5.offset = var23; // L: 853
      var38 = 0; // L: 854
      var39 = 0; // L: 855
      var40 = 0; // L: 856
      var41 = 0; // L: 857

      int var43;
      int var44;
      for (var42 = 0; var42 < var10; ++var42) { // L: 858
         var43 = var5.readUnsignedByte(); // L: 859
         if (var43 == 1) { // L: 860
            var38 = var4.readShortSmart() + var41; // L: 861
            var39 = var4.readShortSmart() + var38; // L: 863
            var40 = var4.readShortSmart() + var39; // L: 865
            var41 = var40; // L: 866
            this.indices1[var42] = var38; // L: 867
            this.indices2[var42] = var39; // L: 868
            this.indices3[var42] = var40; // L: 869
         }

         if (var43 == 2) { // L: 871
            var39 = var40; // L: 872
            var40 = var4.readShortSmart() + var41; // L: 873
            var41 = var40; // L: 874
            this.indices1[var42] = var38; // L: 875
            this.indices2[var42] = var39; // L: 876
            this.indices3[var42] = var40; // L: 877
         }

         if (var43 == 3) { // L: 879
            var38 = var40; // L: 880
            var40 = var4.readShortSmart() + var41; // L: 881
            var41 = var40; // L: 882
            this.indices1[var42] = var38; // L: 883
            this.indices2[var42] = var39; // L: 884
            this.indices3[var42] = var40; // L: 885
         }

         if (var43 == 4) { // L: 887
            var44 = var38; // L: 888
            var38 = var39; // L: 889
            var39 = var44; // L: 890
            var40 = var4.readShortSmart() + var41; // L: 891
            var41 = var40; // L: 892
            this.indices1[var42] = var38; // L: 893
            this.indices2[var42] = var44; // L: 894
            this.indices3[var42] = var40; // L: 895
         }
      }

      var4.offset = var31; // L: 898

      for (var42 = 0; var42 < var11; ++var42) { // L: 899
         this.textureRenderTypes[var42] = 0; // L: 900
         this.texTriangleX[var42] = (short)var4.readUnsignedShort(); // L: 901
         this.texTriangleY[var42] = (short)var4.readUnsignedShort(); // L: 902
         this.texTriangleZ[var42] = (short)var4.readUnsignedShort(); // L: 903
      }

      if (this.textureCoords != null) { // L: 905
         boolean var46 = false; // L: 906

         for (var43 = 0; var43 < var10; ++var43) { // L: 907
            var44 = this.textureCoords[var43] & 255; // L: 908
            if (var44 != 255) { // L: 909
               if (this.indices1[var43] == (this.texTriangleX[var44] & '\uffff') && this.indices2[var43] == (this.texTriangleY[var44] & '\uffff') && this.indices3[var43] == (this.texTriangleZ[var44] & '\uffff')) { // L: 910
                  this.textureCoords[var43] = -1;
               } else {
                  var46 = true; // L: 911
               }
            }
         }

         if (!var46) { // L: 914
            this.textureCoords = null;
         }
      }

      if (!var3) { // L: 916
         this.faceTextures = null;
      }

      if (!var2) { // L: 917
         this.faceRenderTypes = null;
      }

   } // L: 918
   @ObfuscatedSignature(
           signature = "(Ldw;ZZZZ)V",
           garbageValue = "1"
   )
   public ModelData(ModelData var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      this.verticesCount = 0;
      this.faceCount = 0;
      this.priority = 0;
      this.isBoundsCalculated = false;
      this.verticesCount = var1.verticesCount;
      this.faceCount = var1.faceCount;
      this.textureTriangleCount = var1.textureTriangleCount;
      int var6;
      if(var2) {
         this.verticesX = var1.verticesX;
         this.verticesY = var1.verticesY;
         this.verticesZ = var1.verticesZ;
      } else {
         this.verticesX = new int[this.verticesCount];
         this.verticesY = new int[this.verticesCount];
         this.verticesZ = new int[this.verticesCount];

         for(var6 = 0; var6 < this.verticesCount; ++var6) {
            this.verticesX[var6] = var1.verticesX[var6];
            this.verticesY[var6] = var1.verticesY[var6];
            this.verticesZ[var6] = var1.verticesZ[var6];
         }
      }

      if(var3) {
         this.faceColors = var1.faceColors;
      } else {
         this.faceColors = new short[this.faceCount];

         for(var6 = 0; var6 < this.faceCount; ++var6) {
            this.faceColors[var6] = var1.faceColors[var6];
         }
      }

      if(!var4 && var1.faceTextures != null) {
         this.faceTextures = new short[this.faceCount];

         for(var6 = 0; var6 < this.faceCount; ++var6) {
            this.faceTextures[var6] = var1.faceTextures[var6];
         }
      } else {
         this.faceTextures = var1.faceTextures;
      }

      this.faceAlphas = var1.faceAlphas;
      this.indices1 = var1.indices1;
      this.indices2 = var1.indices2;
      this.indices3 = var1.indices3;
      this.faceRenderTypes = var1.faceRenderTypes;
      this.faceRenderPriorities = var1.faceRenderPriorities;
      this.textureCoords = var1.textureCoords;
      this.priority = var1.priority;
      this.textureRenderTypes = var1.textureRenderTypes;
      this.texTriangleX = var1.texTriangleX;
      this.texTriangleY = var1.texTriangleY;
      this.texTriangleZ = var1.texTriangleZ;
      this.vertexSkins = var1.vertexSkins;
      this.faceSkins = var1.faceSkins;
      this.vertexLabels = var1.vertexLabels;
      this.faceLabelsAlpha = var1.faceLabelsAlpha;
      this.vertexNormals = var1.vertexNormals;
      this.faceNormals = var1.faceNormals;
      this.field1636 = var1.field1636;
      this.ambient = var1.ambient;
      this.contrast = var1.contrast;
   }

   @ObfuscatedName("n")
   void method2818(byte[] var1) {
      Buffer var2 = new Buffer(var1);
      Buffer var3 = new Buffer(var1);
      Buffer var4 = new Buffer(var1);
      Buffer var5 = new Buffer(var1);
      Buffer var6 = new Buffer(var1);
      Buffer var7 = new Buffer(var1);
      Buffer var8 = new Buffer(var1);
      var2.offset = var1.length - 23;
      int var9 = var2.readUnsignedShort();
      int var10 = var2.readUnsignedShort();
      int var11 = var2.readUnsignedByte();
      int var12 = var2.readUnsignedByte();
      int var13 = var2.readUnsignedByte();
      int var14 = var2.readUnsignedByte();
      int var15 = var2.readUnsignedByte();
      int var16 = var2.readUnsignedByte();
      int var17 = var2.readUnsignedByte();
      int var18 = var2.readUnsignedShort();
      int var19 = var2.readUnsignedShort();
      int var20 = var2.readUnsignedShort();
      int var21 = var2.readUnsignedShort();
      int var22 = var2.readUnsignedShort();
      int var23 = 0;
      int var24 = 0;
      int var25 = 0;
      int var26;
      if(var11 > 0) {
         this.textureRenderTypes = new byte[var11];
         var2.offset = 0;

         for(var26 = 0; var26 < var11; ++var26) {
            byte var27 = this.textureRenderTypes[var26] = var2.readByte();
            if(var27 == 0) {
               ++var23;
            }

            if(var27 >= 1 && var27 <= 3) {
               ++var24;
            }

            if(var27 == 2) {
               ++var25;
            }
         }
      }

      var26 = var11 + var9;
      int var28 = var26;
      if(var12 == 1) {
         var26 += var10;
      }

      int var29 = var26;
      var26 += var10;
      int var30 = var26;
      if(var13 == 255) {
         var26 += var10;
      }

      int var31 = var26;
      if(var15 == 1) {
         var26 += var10;
      }

      int var32 = var26;
      if(var17 == 1) {
         var26 += var9;
      }

      int var33 = var26;
      if(var14 == 1) {
         var26 += var10;
      }

      int var34 = var26;
      var26 += var21;
      int var35 = var26;
      if(var16 == 1) {
         var26 += var10 * 2;
      }

      int var36 = var26;
      var26 += var22;
      int var37 = var26;
      var26 += var10 * 2;
      int var38 = var26;
      var26 += var18;
      int var39 = var26;
      var26 += var19;
      int var40 = var26;
      var26 += var20;
      int var41 = var26;
      var26 += var23 * 6;
      int var42 = var26;
      var26 += var24 * 6;
      int var43 = var26;
      var26 += var24 * 6;
      int var44 = var26;
      var26 += var24 * 2;
      int var45 = var26;
      var26 += var24;
      int var46 = var26;
      var26 += var24 * 2 + var25 * 2;
      this.verticesCount = var9;
      this.faceCount = var10;
      this.textureTriangleCount = var11;
      this.verticesX = new int[var9];
      this.verticesY = new int[var9];
      this.verticesZ = new int[var9];
      this.indices1 = new int[var10];
      this.indices2 = new int[var10];
      this.indices3 = new int[var10];
      if(var17 == 1) {
         this.vertexSkins = new int[var9];
      }

      if(var12 == 1) {
         this.faceRenderTypes = new byte[var10];
      }

      if(var13 == 255) {
         this.faceRenderPriorities = new byte[var10];
      } else {
         this.priority = (byte)var13;
      }

      if(var14 == 1) {
         this.faceAlphas = new byte[var10];
      }

      if(var15 == 1) {
         this.faceSkins = new int[var10];
      }

      if(var16 == 1) {
         this.faceTextures = new short[var10];
      }

      if(var16 == 1 && var11 > 0) {
         this.textureCoords = new byte[var10];
      }

      this.faceColors = new short[var10];
      if(var11 > 0) {
         this.texTriangleX = new short[var11];
         this.texTriangleY = new short[var11];
         this.texTriangleZ = new short[var11];
      }

      var2.offset = var11;
      var3.offset = var38;
      var4.offset = var39;
      var5.offset = var40;
      var6.offset = var32;
      int var48 = 0;
      int var49 = 0;
      int var50 = 0;

      int var51;
      int var52;
      int var53;
      int var54;
      int var55;
      for(var51 = 0; var51 < var9; ++var51) {
         var52 = var2.readUnsignedByte();
         var53 = 0;
         if((var52 & 1) != 0) {
            var53 = var3.method5509();
         }

         var54 = 0;
         if((var52 & 2) != 0) {
            var54 = var4.method5509();
         }

         var55 = 0;
         if((var52 & 4) != 0) {
            var55 = var5.method5509();
         }

         this.verticesX[var51] = var48 + var53;
         this.verticesY[var51] = var49 + var54;
         this.verticesZ[var51] = var50 + var55;
         var48 = this.verticesX[var51];
         var49 = this.verticesY[var51];
         var50 = this.verticesZ[var51];
         if(var17 == 1) {
            this.vertexSkins[var51] = var6.readUnsignedByte();
         }
      }

      var2.offset = var37;
      var3.offset = var28;
      var4.offset = var30;
      var5.offset = var33;
      var6.offset = var31;
      var7.offset = var35;
      var8.offset = var36;

      for(var51 = 0; var51 < var10; ++var51) {
         this.faceColors[var51] = (short)var2.readUnsignedShort();
         if(var12 == 1) {
            this.faceRenderTypes[var51] = var3.readByte();
         }

         if(var13 == 255) {
            this.faceRenderPriorities[var51] = var4.readByte();
         }

         if(var14 == 1) {
            this.faceAlphas[var51] = var5.readByte();
         }

         if(var15 == 1) {
            this.faceSkins[var51] = var6.readUnsignedByte();
         }

         if(var16 == 1) {
            this.faceTextures[var51] = (short)(var7.readUnsignedShort() - 1);
         }

         if(this.textureCoords != null && this.faceTextures[var51] != -1) {
            this.textureCoords[var51] = (byte)(var8.readUnsignedByte() - 1);
         }
      }

      var2.offset = var34;
      var3.offset = var29;
      var51 = 0;
      var52 = 0;
      var53 = 0;
      var54 = 0;

      int var56;
      for(var55 = 0; var55 < var10; ++var55) {
         var56 = var3.readUnsignedByte();
         if(var56 == 1) {
            var51 = var2.method5509() + var54;
            var52 = var2.method5509() + var51;
            var53 = var2.method5509() + var52;
            var54 = var53;
            this.indices1[var55] = var51;
            this.indices2[var55] = var52;
            this.indices3[var55] = var53;
         }

         if(var56 == 2) {
            var52 = var53;
            var53 = var2.method5509() + var54;
            var54 = var53;
            this.indices1[var55] = var51;
            this.indices2[var55] = var52;
            this.indices3[var55] = var53;
         }

         if(var56 == 3) {
            var51 = var53;
            var53 = var2.method5509() + var54;
            var54 = var53;
            this.indices1[var55] = var51;
            this.indices2[var55] = var52;
            this.indices3[var55] = var53;
         }

         if(var56 == 4) {
            int var57 = var51;
            var51 = var52;
            var52 = var57;
            var53 = var2.method5509() + var54;
            var54 = var53;
            this.indices1[var55] = var51;
            this.indices2[var55] = var57;
            this.indices3[var55] = var53;
         }
      }

      var2.offset = var41;
      var3.offset = var42;
      var4.offset = var43;
      var5.offset = var44;
      var6.offset = var45;
      var7.offset = var46;

      for(var55 = 0; var55 < var11; ++var55) {
         var56 = this.textureRenderTypes[var55] & 255;
         if(var56 == 0) {
            this.texTriangleX[var55] = (short)var2.readUnsignedShort();
            this.texTriangleY[var55] = (short)var2.readUnsignedShort();
            this.texTriangleZ[var55] = (short)var2.readUnsignedShort();
         }
      }

      var2.offset = var26;
      var55 = var2.readUnsignedByte();
      if(var55 != 0) {
         new ModelData0();
         var2.readUnsignedShort();
         var2.readUnsignedShort();
         var2.readUnsignedShort();
         var2.readInt();
      }

   }

   @ObfuscatedName("o")
   public void method2784(int var1, int var2, int var3) {
      for(int var4 = 0; var4 < this.verticesCount; ++var4) {
         this.verticesX[var4] += var1;
         this.verticesY[var4] += var2;
         this.verticesZ[var4] += var3;
      }

      this.method2775();
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
           signature = "([[IIIIZI)Ldw;"
   )
   public ModelData method2763(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      this.method2776();
      int var7 = var2 + this.field1635;
      int var8 = var2 + this.field1604;
      int var9 = var4 + this.field1642;
      int var10 = var4 + this.field1637;
      if(var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
         var7 >>= 7;
         var8 = var8 + 127 >> 7;
         var9 >>= 7;
         var10 = var10 + 127 >> 7;
         if(var3 == var1[var7][var9] && var3 == var1[var8][var9] && var3 == var1[var7][var10] && var3 == var1[var8][var10]) {
            return this;
         } else {
            ModelData var11 = new ModelData();
            var11.verticesCount = this.verticesCount;
            var11.faceCount = this.faceCount;
            var11.textureTriangleCount = this.textureTriangleCount;
            var11.verticesX = this.verticesX;
            var11.verticesZ = this.verticesZ;
            var11.indices1 = this.indices1;
            var11.indices2 = this.indices2;
            var11.indices3 = this.indices3;
            var11.faceRenderTypes = this.faceRenderTypes;
            var11.faceRenderPriorities = this.faceRenderPriorities;
            var11.faceAlphas = this.faceAlphas;
            var11.textureCoords = this.textureCoords;
            var11.faceColors = this.faceColors;
            var11.faceTextures = this.faceTextures;
            var11.priority = this.priority;
            var11.textureRenderTypes = this.textureRenderTypes;
            var11.texTriangleX = this.texTriangleX;
            var11.texTriangleY = this.texTriangleY;
            var11.texTriangleZ = this.texTriangleZ;
            var11.vertexSkins = this.vertexSkins;
            var11.faceSkins = this.faceSkins;
            var11.vertexLabels = this.vertexLabels;
            var11.faceLabelsAlpha = this.faceLabelsAlpha;
            var11.ambient = this.ambient;
            var11.contrast = this.contrast;
            var11.verticesY = new int[var11.verticesCount];
            int var12;
            int var13;
            int var14;
            int var15;
            int var16;
            int var17;
            int var18;
            int var19;
            int var20;
            int var21;
            if(var6 == 0) {
               for(var12 = 0; var12 < var11.verticesCount; ++var12) {
                  var13 = var2 + this.verticesX[var12];
                  var14 = var4 + this.verticesZ[var12];
                  var15 = var13 & 127;
                  var16 = var14 & 127;
                  var17 = var13 >> 7;
                  var18 = var14 >> 7;
                  var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                  var20 = var1[var17][var18 + 1] * (128 - var15) + var15 * var1[var17 + 1][var18 + 1] >> 7;
                  var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                  var11.verticesY[var12] = var21 + this.verticesY[var12] - var3;
               }
            } else {
               for(var12 = 0; var12 < var11.verticesCount; ++var12) {
                  var13 = (-this.verticesY[var12] << 16) / super.height;
                  if(var13 < var6) {
                     var14 = var2 + this.verticesX[var12];
                     var15 = var4 + this.verticesZ[var12];
                     var16 = var14 & 127;
                     var17 = var15 & 127;
                     var18 = var14 >> 7;
                     var19 = var15 >> 7;
                     var20 = var1[var18][var19] * (128 - var16) + var1[var18 + 1][var19] * var16 >> 7;
                     var21 = var1[var18][var19 + 1] * (128 - var16) + var16 * var1[var18 + 1][var19 + 1] >> 7;
                     int var22 = var20 * (128 - var17) + var21 * var17 >> 7;
                     var11.verticesY[var12] = (var6 - var13) * (var22 - var3) / var6 + this.verticesY[var12];
                  }
               }
            }

            var11.method2775();
            return var11;
         }
      } else {
         return this;
      }
   }

   @ObfuscatedName("q")
   void method2764() {
      int[] var1;
      int var2;
      int var3;
      int var4;
      if(this.vertexSkins != null) {
         var1 = new int[256];
         var2 = 0;

         for(var3 = 0; var3 < this.verticesCount; ++var3) {
            var4 = this.vertexSkins[var3];
            ++var1[var4];
            if(var4 > var2) {
               var2 = var4;
            }
         }

         this.vertexLabels = new int[var2 + 1][];

         for(var3 = 0; var3 <= var2; ++var3) {
            this.vertexLabels[var3] = new int[var1[var3]];
            var1[var3] = 0;
         }

         for(var3 = 0; var3 < this.verticesCount; this.vertexLabels[var4][var1[var4]++] = var3++) {
            var4 = this.vertexSkins[var3];
         }

         this.vertexSkins = null;
      }

      if(this.faceSkins != null) {
         var1 = new int[256];
         var2 = 0;

         for(var3 = 0; var3 < this.faceCount; ++var3) {
            var4 = this.faceSkins[var3];
            ++var1[var4];
            if(var4 > var2) {
               var2 = var4;
            }
         }

         this.faceLabelsAlpha = new int[var2 + 1][];

         for(var3 = 0; var3 <= var2; ++var3) {
            this.faceLabelsAlpha[var3] = new int[var1[var3]];
            var1[var3] = 0;
         }

         for(var3 = 0; var3 < this.faceCount; this.faceLabelsAlpha[var4][var1[var4]++] = var3++) {
            var4 = this.faceSkins[var3];
         }

         this.faceSkins = null;
      }

   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
           signature = "()Ldw;"
   )
   public ModelData method2762() {
      ModelData var1 = new ModelData();
      if(this.faceRenderTypes != null) {
         var1.faceRenderTypes = new byte[this.faceCount];

         for(int var2 = 0; var2 < this.faceCount; ++var2) {
            var1.faceRenderTypes[var2] = this.faceRenderTypes[var2];
         }
      }

      var1.verticesCount = this.verticesCount;
      var1.faceCount = this.faceCount;
      var1.textureTriangleCount = this.textureTriangleCount;
      var1.verticesX = this.verticesX;
      var1.verticesY = this.verticesY;
      var1.verticesZ = this.verticesZ;
      var1.indices1 = this.indices1;
      var1.indices2 = this.indices2;
      var1.indices3 = this.indices3;
      var1.faceRenderPriorities = this.faceRenderPriorities;
      var1.faceAlphas = this.faceAlphas;
      var1.textureCoords = this.textureCoords;
      var1.faceColors = this.faceColors;
      var1.faceTextures = this.faceTextures;
      var1.priority = this.priority;
      var1.textureRenderTypes = this.textureRenderTypes;
      var1.texTriangleX = this.texTriangleX;
      var1.texTriangleY = this.texTriangleY;
      var1.texTriangleZ = this.texTriangleZ;
      var1.vertexSkins = this.vertexSkins;
      var1.faceSkins = this.faceSkins;
      var1.vertexLabels = this.vertexLabels;
      var1.faceLabelsAlpha = this.faceLabelsAlpha;
      var1.vertexNormals = this.vertexNormals;
      var1.faceNormals = this.faceNormals;
      var1.ambient = this.ambient;
      var1.contrast = this.contrast;
      return var1;
   }

   @ObfuscatedName("s")
   public void method2774() {
      if(this.vertexNormals == null) {
         this.vertexNormals = new VertexNormal[this.verticesCount];

         int var1;
         for(var1 = 0; var1 < this.verticesCount; ++var1) {
            this.vertexNormals[var1] = new VertexNormal();
         }

         for(var1 = 0; var1 < this.faceCount; ++var1) {
            int var2 = this.indices1[var1];
            int var3 = this.indices2[var1];
            int var4 = this.indices3[var1];
            int var5 = this.verticesX[var3] - this.verticesX[var2];
            int var6 = this.verticesY[var3] - this.verticesY[var2];
            int var7 = this.verticesZ[var3] - this.verticesZ[var2];
            int var8 = this.verticesX[var4] - this.verticesX[var2];
            int var9 = this.verticesY[var4] - this.verticesY[var2];
            int var10 = this.verticesZ[var4] - this.verticesZ[var2];
            int var11 = var6 * var10 - var9 * var7;
            int var12 = var7 * var8 - var10 * var5;

            int var13;
            for(var13 = var5 * var9 - var8 * var6; var11 > 8192 || var12 > 8192 || var13 > 8192 || var11 < -8192 || var12 < -8192 || var13 < -8192; var13 >>= 1) {
               var11 >>= 1;
               var12 >>= 1;
            }

            int var14 = (int)Math.sqrt((double)(var11 * var11 + var12 * var12 + var13 * var13));
            if(var14 <= 0) {
               var14 = 1;
            }

            var11 = var11 * 256 / var14;
            var12 = var12 * 256 / var14;
            var13 = var13 * 256 / var14;
            byte var15;
            if(this.faceRenderTypes == null) {
               var15 = 0;
            } else {
               var15 = this.faceRenderTypes[var1];
            }

            if(var15 == 0) {
               VertexNormal var16 = this.vertexNormals[var2];
               var16.x += var11;
               var16.y += var12;
               var16.z += var13;
               ++var16.magnitude;
               var16 = this.vertexNormals[var3];
               var16.x += var11;
               var16.y += var12;
               var16.z += var13;
               ++var16.magnitude;
               var16 = this.vertexNormals[var4];
               var16.x += var11;
               var16.y += var12;
               var16.z += var13;
               ++var16.magnitude;
            } else if(var15 == 1) {
               if(this.faceNormals == null) {
                  this.faceNormals = new FaceNormal[this.faceCount];
               }

               FaceNormal var17 = this.faceNormals[var1] = new FaceNormal();
               var17.x = var11;
               var17.y = var12;
               var17.z = var13;
            }
         }

      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
           signature = "(Ldw;I)I"
   )
   final int method2761(ModelData var1, int var2) {
      int var3 = -1;
      int var4 = var1.verticesX[var2];
      int var5 = var1.verticesY[var2];
      int var6 = var1.verticesZ[var2];

      for(int var7 = 0; var7 < this.verticesCount; ++var7) {
         if(var4 == this.verticesX[var7] && var5 == this.verticesY[var7] && var6 == this.verticesZ[var7]) {
            var3 = var7;
            break;
         }
      }

      if(var3 == -1) {
         this.verticesX[this.verticesCount] = var4;
         this.verticesY[this.verticesCount] = var5;
         this.verticesZ[this.verticesCount] = var6;
         if(var1.vertexSkins != null) {
            this.vertexSkins[this.verticesCount] = var1.vertexSkins[var2];
         }

         var3 = this.verticesCount++;
      }

      return var3;
   }

   @ObfuscatedName("v")
   void method2760(byte[] var1) {
      boolean var2 = false;
      boolean var3 = false;
      Buffer var4 = new Buffer(var1);
      Buffer var5 = new Buffer(var1);
      Buffer var6 = new Buffer(var1);
      Buffer var7 = new Buffer(var1);
      Buffer var8 = new Buffer(var1);
      var4.offset = var1.length - 18;
      int var9 = var4.readUnsignedShort();
      int var10 = var4.readUnsignedShort();
      int var11 = var4.readUnsignedByte();
      int var12 = var4.readUnsignedByte();
      int var13 = var4.readUnsignedByte();
      int var14 = var4.readUnsignedByte();
      int var15 = var4.readUnsignedByte();
      int var16 = var4.readUnsignedByte();
      int var17 = var4.readUnsignedShort();
      int var18 = var4.readUnsignedShort();
      int var19 = var4.readUnsignedShort();
      int var20 = var4.readUnsignedShort();
      byte var21 = 0;
      int var45 = var21 + var9;
      int var23 = var45;
      var45 += var10;
      int var24 = var45;
      if(var13 == 255) {
         var45 += var10;
      }

      int var25 = var45;
      if(var15 == 1) {
         var45 += var10;
      }

      int var26 = var45;
      if(var12 == 1) {
         var45 += var10;
      }

      int var27 = var45;
      if(var16 == 1) {
         var45 += var9;
      }

      int var28 = var45;
      if(var14 == 1) {
         var45 += var10;
      }

      int var29 = var45;
      var45 += var20;
      int var30 = var45;
      var45 += var10 * 2;
      int var31 = var45;
      var45 += var11 * 6;
      int var32 = var45;
      var45 += var17;
      int var33 = var45;
      var45 += var18;
      int var10000 = var45 + var19;
      this.verticesCount = var9;
      this.faceCount = var10;
      this.textureTriangleCount = var11;
      this.verticesX = new int[var9];
      this.verticesY = new int[var9];
      this.verticesZ = new int[var9];
      this.indices1 = new int[var10];
      this.indices2 = new int[var10];
      this.indices3 = new int[var10];
      if(var11 > 0) {
         this.textureRenderTypes = new byte[var11];
         this.texTriangleX = new short[var11];
         this.texTriangleY = new short[var11];
         this.texTriangleZ = new short[var11];
      }

      if(var16 == 1) {
         this.vertexSkins = new int[var9];
      }

      if(var12 == 1) {
         this.faceRenderTypes = new byte[var10];
         this.textureCoords = new byte[var10];
         this.faceTextures = new short[var10];
      }

      if(var13 == 255) {
         this.faceRenderPriorities = new byte[var10];
      } else {
         this.priority = (byte)var13;
      }

      if(var14 == 1) {
         this.faceAlphas = new byte[var10];
      }

      if(var15 == 1) {
         this.faceSkins = new int[var10];
      }

      this.faceColors = new short[var10];
      var4.offset = var21;
      var5.offset = var32;
      var6.offset = var33;
      var7.offset = var45;
      var8.offset = var27;
      int var35 = 0;
      int var36 = 0;
      int var37 = 0;

      int var38;
      int var39;
      int var40;
      int var41;
      int var42;
      for(var38 = 0; var38 < var9; ++var38) {
         var39 = var4.readUnsignedByte();
         var40 = 0;
         if((var39 & 1) != 0) {
            var40 = var5.method5509();
         }

         var41 = 0;
         if((var39 & 2) != 0) {
            var41 = var6.method5509();
         }

         var42 = 0;
         if((var39 & 4) != 0) {
            var42 = var7.method5509();
         }

         this.verticesX[var38] = var35 + var40;
         this.verticesY[var38] = var36 + var41;
         this.verticesZ[var38] = var37 + var42;
         var35 = this.verticesX[var38];
         var36 = this.verticesY[var38];
         var37 = this.verticesZ[var38];
         if(var16 == 1) {
            this.vertexSkins[var38] = var8.readUnsignedByte();
         }
      }

      var4.offset = var30;
      var5.offset = var26;
      var6.offset = var24;
      var7.offset = var28;
      var8.offset = var25;

      for(var38 = 0; var38 < var10; ++var38) {
         this.faceColors[var38] = (short)var4.readUnsignedShort();
         if(var12 == 1) {
            var39 = var5.readUnsignedByte();
            if((var39 & 1) == 1) {
               this.faceRenderTypes[var38] = 1;
               var2 = true;
            } else {
               this.faceRenderTypes[var38] = 0;
            }

            if((var39 & 2) == 2) {
               this.textureCoords[var38] = (byte)(var39 >> 2);
               this.faceTextures[var38] = this.faceColors[var38];
               this.faceColors[var38] = 127;
               if(this.faceTextures[var38] != -1) {
                  var3 = true;
               }
            } else {
               this.textureCoords[var38] = -1;
               this.faceTextures[var38] = -1;
            }
         }

         if(var13 == 255) {
            this.faceRenderPriorities[var38] = var6.readByte();
         }

         if(var14 == 1) {
            this.faceAlphas[var38] = var7.readByte();
         }

         if(var15 == 1) {
            this.faceSkins[var38] = var8.readUnsignedByte();
         }
      }

      var4.offset = var29;
      var5.offset = var23;
      var38 = 0;
      var39 = 0;
      var40 = 0;
      var41 = 0;

      int var43;
      int var44;
      for(var42 = 0; var42 < var10; ++var42) {
         var43 = var5.readUnsignedByte();
         if(var43 == 1) {
            var38 = var4.method5509() + var41;
            var39 = var4.method5509() + var38;
            var40 = var4.method5509() + var39;
            var41 = var40;
            this.indices1[var42] = var38;
            this.indices2[var42] = var39;
            this.indices3[var42] = var40;
         }

         if(var43 == 2) {
            var39 = var40;
            var40 = var4.method5509() + var41;
            var41 = var40;
            this.indices1[var42] = var38;
            this.indices2[var42] = var39;
            this.indices3[var42] = var40;
         }

         if(var43 == 3) {
            var38 = var40;
            var40 = var4.method5509() + var41;
            var41 = var40;
            this.indices1[var42] = var38;
            this.indices2[var42] = var39;
            this.indices3[var42] = var40;
         }

         if(var43 == 4) {
            var44 = var38;
            var38 = var39;
            var39 = var44;
            var40 = var4.method5509() + var41;
            var41 = var40;
            this.indices1[var42] = var38;
            this.indices2[var42] = var44;
            this.indices3[var42] = var40;
         }
      }

      var4.offset = var31;

      for(var42 = 0; var42 < var11; ++var42) {
         this.textureRenderTypes[var42] = 0;
         this.texTriangleX[var42] = (short)var4.readUnsignedShort();
         this.texTriangleY[var42] = (short)var4.readUnsignedShort();
         this.texTriangleZ[var42] = (short)var4.readUnsignedShort();
      }

      if(this.textureCoords != null) {
         boolean var46 = false;

         for(var43 = 0; var43 < var10; ++var43) {
            var44 = this.textureCoords[var43] & 255;
            if(var44 != 255) {
               if(this.indices1[var43] == (this.texTriangleX[var44] & '\uffff') && this.indices2[var43] == (this.texTriangleY[var44] & '\uffff') && this.indices3[var43] == (this.texTriangleZ[var44] & '\uffff')) {
                  this.textureCoords[var43] = -1;
               } else {
                  var46 = true;
               }
            }
         }

         if(!var46) {
            this.textureCoords = null;
         }
      }

      if(!var3) {
         this.faceTextures = null;
      }

      if(!var2) {
         this.faceRenderTypes = null;
      }

   }

   @ObfuscatedName("x")
   public void method2772() {
      int var1;
      for(var1 = 0; var1 < this.verticesCount; ++var1) {
         this.verticesZ[var1] = -this.verticesZ[var1];
      }

      for(var1 = 0; var1 < this.faceCount; ++var1) {
         int var2 = this.indices1[var1];
         this.indices1[var1] = this.indices3[var1];
         this.indices3[var1] = var2;
      }

      this.method2775();
   }

   @ObfuscatedName("a")
   public void method2770(short var1, short var2) {
      for(int var3 = 0; var3 < this.faceCount; ++var3) {
         if(this.faceColors[var3] == var1) {
            this.faceColors[var3] = var2;
         }
      }

   }

   @ObfuscatedName("b")
   public void method2765(int var1) {
      int var2 = ModelData_sine[var1];
      int var3 = ModelData_cosine[var1];

      for(int var4 = 0; var4 < this.verticesCount; ++var4) {
         int var5 = var2 * this.verticesZ[var4] + var3 * this.verticesX[var4] >> 16;
         this.verticesZ[var4] = var3 * this.verticesZ[var4] - var2 * this.verticesX[var4] >> 16;
         this.verticesX[var4] = var5;
      }

      this.method2775();
   }

   @ObfuscatedName("c")
   public void method2798() {
      for(int var1 = 0; var1 < this.verticesCount; ++var1) {
         int var2 = this.verticesZ[var1];
         this.verticesZ[var1] = this.verticesX[var1];
         this.verticesX[var1] = -var2;
      }

      this.method2775();
   }

   public short[] getFaceTextures() {
      return this.faceTextures;
   }

   public int[] getVertexX() {
      return this.verticesX;
   }

   @ObfuscatedSignature(
           signature = "(IIIII)Ldh;"
   )
   public final Model copy$toModel(int var1, int var2, int var3, int var4, int var5) {
      this.method2774();
      int var6 = (int)Math.sqrt((double)(var5 * var5 + var3 * var3 + var4 * var4));
      int var7 = var6 * var2 >> 8;
      Model var8 = new Model();
      var8.faceColors1 = new int[this.faceCount];
      var8.faceColors2 = new int[this.faceCount];
      var8.faceColors3 = new int[this.faceCount];
      if(this.textureTriangleCount > 0 && this.textureCoords != null) {
         int[] var9 = new int[this.textureTriangleCount];

         int var10;
         for(var10 = 0; var10 < this.faceCount; ++var10) {
            if(this.textureCoords[var10] != -1) {
               ++var9[this.textureCoords[var10] & 255];
            }
         }

         var8.field1425 = 0;

         for(var10 = 0; var10 < this.textureTriangleCount; ++var10) {
            if(var9[var10] > 0 && this.textureRenderTypes[var10] == 0) {
               ++var8.field1425;
            }
         }

         var8.field1394 = new int[var8.field1425];
         var8.field1395 = new int[var8.field1425];
         var8.field1393 = new int[var8.field1425];
         var10 = 0;

         int var11;
         for(var11 = 0; var11 < this.textureTriangleCount; ++var11) {
            if(var9[var11] > 0 && this.textureRenderTypes[var11] == 0) {
               var8.field1394[var10] = this.texTriangleX[var11] & '\uffff';
               var8.field1395[var10] = this.texTriangleY[var11] & '\uffff';
               var8.field1393[var10] = this.texTriangleZ[var11] & '\uffff';
               var9[var11] = var10++;
            } else {
               var9[var11] = -1;
            }
         }

         var8.field1379 = new byte[this.faceCount];

         for(var11 = 0; var11 < this.faceCount; ++var11) {
            if(this.textureCoords[var11] != -1) {
               var8.field1379[var11] = (byte)var9[this.textureCoords[var11] & 255];
            } else {
               var8.field1379[var11] = -1;
            }
         }
      }

      for(int var16 = 0; var16 < this.faceCount; ++var16) {
         byte var17;
         if(this.faceRenderTypes == null) {
            var17 = 0;
         } else {
            var17 = this.faceRenderTypes[var16];
         }

         byte var18;
         if(this.faceAlphas == null) {
            var18 = 0;
         } else {
            var18 = this.faceAlphas[var16];
         }

         short var12;
         if(this.faceTextures == null) {
            var12 = -1;
         } else {
            var12 = this.faceTextures[var16];
         }

         if(var18 == -2) {
            var17 = 3;
         }

         if(var18 == -1) {
            var17 = 2;
         }

         VertexNormal var13;
         int var14;
         FaceNormal var19;
         if(var12 == -1) {
            if(var17 != 0) {
               if(var17 == 1) {
                  var19 = this.faceNormals[var16];
                  var14 = (var4 * var19.y + var5 * var19.z + var3 * var19.x) / (var7 / 2 + var7) + var1;
                  var8.faceColors1[var16] = method2779(this.faceColors[var16] & '\uffff', var14);
                  var8.faceColors3[var16] = -1;
               } else if(var17 == 3) {
                  var8.faceColors1[var16] = 128;
                  var8.faceColors3[var16] = -1;
               } else {
                  var8.faceColors3[var16] = -2;
               }
            } else {
               int var15 = this.faceColors[var16] & '\uffff';
               if(this.field1636 != null && this.field1636[this.indices1[var16]] != null) {
                  var13 = this.field1636[this.indices1[var16]];
               } else {
                  var13 = this.vertexNormals[this.indices1[var16]];
               }

               var14 = (var4 * var13.y + var5 * var13.z + var3 * var13.x) / (var7 * var13.magnitude) + var1;
               var8.faceColors1[var16] = method2779(var15, var14);
               if(this.field1636 != null && this.field1636[this.indices2[var16]] != null) {
                  var13 = this.field1636[this.indices2[var16]];
               } else {
                  var13 = this.vertexNormals[this.indices2[var16]];
               }

               var14 = (var4 * var13.y + var5 * var13.z + var3 * var13.x) / (var7 * var13.magnitude) + var1;
               var8.faceColors2[var16] = method2779(var15, var14);
               if(this.field1636 != null && this.field1636[this.indices3[var16]] != null) {
                  var13 = this.field1636[this.indices3[var16]];
               } else {
                  var13 = this.vertexNormals[this.indices3[var16]];
               }

               var14 = (var4 * var13.y + var5 * var13.z + var3 * var13.x) / (var7 * var13.magnitude) + var1;
               var8.faceColors3[var16] = method2779(var15, var14);
            }
         } else if(var17 != 0) {
            if(var17 == 1) {
               var19 = this.faceNormals[var16];
               var14 = (var4 * var19.y + var5 * var19.z + var3 * var19.x) / (var7 / 2 + var7) + var1;
               var8.faceColors1[var16] = method2769(var14);
               var8.faceColors3[var16] = -1;
            } else {
               var8.faceColors3[var16] = -2;
            }
         } else {
            if(this.field1636 != null && this.field1636[this.indices1[var16]] != null) {
               var13 = this.field1636[this.indices1[var16]];
            } else {
               var13 = this.vertexNormals[this.indices1[var16]];
            }

            var14 = (var4 * var13.y + var5 * var13.z + var3 * var13.x) / (var7 * var13.magnitude) + var1;
            var8.faceColors1[var16] = method2769(var14);
            if(this.field1636 != null && this.field1636[this.indices2[var16]] != null) {
               var13 = this.field1636[this.indices2[var16]];
            } else {
               var13 = this.vertexNormals[this.indices2[var16]];
            }

            var14 = (var4 * var13.y + var5 * var13.z + var3 * var13.x) / (var7 * var13.magnitude) + var1;
            var8.faceColors2[var16] = method2769(var14);
            if(this.field1636 != null && this.field1636[this.indices3[var16]] != null) {
               var13 = this.field1636[this.indices3[var16]];
            } else {
               var13 = this.vertexNormals[this.indices3[var16]];
            }

            var14 = (var4 * var13.y + var5 * var13.z + var3 * var13.x) / (var7 * var13.magnitude) + var1;
            var8.faceColors3[var16] = method2769(var14);
         }
      }

      this.method2764();
      var8.verticesCount = this.verticesCount;
      var8.verticesX = this.verticesX;
      var8.verticesY = this.verticesY;
      var8.verticesZ = this.verticesZ;
      var8.indicesCount = this.faceCount;
      var8.indices1 = this.indices1;
      var8.indices2 = this.indices2;
      var8.indices3 = this.indices3;
      var8.faceRenderPriorities = this.faceRenderPriorities;
      var8.faceAlphas = this.faceAlphas;
      var8.field1374 = this.priority;
      var8.vertexLabels = this.vertexLabels;
      var8.faceLabelsAlpha = this.faceLabelsAlpha;
      var8.faceTextures = this.faceTextures;
      return var8;
   }

   public int[] getVertexY() {
      return this.verticesY;
   }

   public int[] getVertexZ() {
      return this.verticesZ;
   }

   public void computeTextureUVCoordinates() {
      short[] var1 = this.getFaceTextures();
      if(var1 != null) {
         int[] var2 = this.getVertexX();
         int[] var3 = this.getVertexY();
         int[] var4 = this.getVertexZ();
         int[] var5 = this.getTrianglePointsX();
         int[] var6 = this.getTrianglePointsY();
         int[] var7 = this.getTrianglePointsZ();
         short[] var8 = this.getTexTriangleX();
         short[] var9 = this.getTexTriangleY();
         short[] var10 = this.getTexTriangleZ();
         byte[] var11 = this.getTextureCoords();
         byte[] var12 = this.getTextureRenderTypes();
         int var13 = this.getTriangleFaceCount();
         this.faceTextureUCoordinates = new float[var13][];
         this.faceTextureVCoordinates = new float[var13][];

         for(int var14 = 0; var14 < var13; ++var14) {
            byte var15;
            if(var11 == null) {
               var15 = -1;
            } else {
               var15 = var11[var14];
            }

            short var16 = var1[var14];
            if(var16 != -1) {
               float[] var17 = new float[3];
               float[] var18 = new float[3];
               if(var15 == -1) {
                  var17[0] = 0.0F;
                  var18[0] = 1.0F;
                  var17[1] = 1.0F;
                  var18[1] = 1.0F;
                  var17[2] = 0.0F;
                  var18[2] = 0.0F;
               } else {
                  int var51 = var15 & 255;
                  byte var19 = 0;
                  if(var12 != null) {
                     var19 = var12[var51];
                  }

                  if(var19 == 0) {
                     int var20 = var5[var14];
                     int var21 = var6[var14];
                     int var22 = var7[var14];
                     short var23 = var8[var51];
                     short var24 = var9[var51];
                     short var25 = var10[var51];
                     float var26 = (float)var2[var23];
                     float var27 = (float)var3[var23];
                     float var28 = (float)var4[var23];
                     float var29 = (float)var2[var24] - var26;
                     float var30 = (float)var3[var24] - var27;
                     float var31 = (float)var4[var24] - var28;
                     float var32 = (float)var2[var25] - var26;
                     float var33 = (float)var3[var25] - var27;
                     float var34 = (float)var4[var25] - var28;
                     float var35 = (float)var2[var20] - var26;
                     float var36 = (float)var3[var20] - var27;
                     float var37 = (float)var4[var20] - var28;
                     float var38 = (float)var2[var21] - var26;
                     float var39 = (float)var3[var21] - var27;
                     float var40 = (float)var4[var21] - var28;
                     float var41 = (float)var2[var22] - var26;
                     float var42 = (float)var3[var22] - var27;
                     float var43 = (float)var4[var22] - var28;
                     float var44 = var30 * var34 - var31 * var33;
                     float var45 = var31 * var32 - var29 * var34;
                     float var46 = var29 * var33 - var30 * var32;
                     float var47 = var33 * var46 - var34 * var45;
                     float var48 = var34 * var44 - var32 * var46;
                     float var49 = var32 * var45 - var33 * var44;
                     float var50 = 1.0F / (var47 * var29 + var48 * var30 + var49 * var31);
                     var17[0] = (var47 * var35 + var48 * var36 + var49 * var37) * var50;
                     var17[1] = (var47 * var38 + var48 * var39 + var49 * var40) * var50;
                     var17[2] = (var47 * var41 + var48 * var42 + var49 * var43) * var50;
                     var47 = var30 * var46 - var31 * var45;
                     var48 = var31 * var44 - var29 * var46;
                     var49 = var29 * var45 - var30 * var44;
                     var50 = 1.0F / (var47 * var32 + var48 * var33 + var49 * var34);
                     var18[0] = (var47 * var35 + var48 * var36 + var49 * var37) * var50;
                     var18[1] = (var47 * var38 + var48 * var39 + var49 * var40) * var50;
                     var18[2] = (var47 * var41 + var48 * var42 + var49 * var43) * var50;
                  }
               }

               this.faceTextureUCoordinates[var14] = var17;
               this.faceTextureVCoordinates[var14] = var18;
            }
         }

      }
   }

   public int[] getTrianglePointsX() {
      return this.indices1;
   }

   public int[] getTrianglePointsY() {
      return this.indices2;
   }

   public int[] getTrianglePointsZ() {
      return this.indices3;
   }

   public short[] getTexTriangleX() {
      return this.texTriangleX;
   }

   public short[] getTexTriangleY() {
      return this.texTriangleY;
   }

   public short[] getTexTriangleZ() {
      return this.texTriangleZ;
   }

   public byte[] getTextureCoords() {
      return this.textureCoords;
   }

   public byte[] getTextureRenderTypes() {
      return this.textureRenderTypes;
   }

   public int getTriangleFaceCount() {
      return this.faceCount;
   }

   @ObfuscatedName("e")
   public void method2831(short var1, short var2) {
      if(this.faceTextures != null) {
         for(int var3 = 0; var3 < this.faceCount; ++var3) {
            if(this.faceTextures[var3] == var1) {
               this.faceTextures[var3] = var2;
            }
         }

      }
   }

   @ObfuscatedName("f")
   void method2775() {
      this.vertexNormals = null;
      this.field1636 = null;
      this.faceNormals = null;
      this.isBoundsCalculated = false;
   }

   @ObfuscatedName("h")
   public void method2773(int var1, int var2, int var3) {
      for(int var4 = 0; var4 < this.verticesCount; ++var4) {
         this.verticesX[var4] = this.verticesX[var4] * var1 / 128;
         this.verticesY[var4] = var2 * this.verticesY[var4] / 128;
         this.verticesZ[var4] = var3 * this.verticesZ[var4] / 128;
      }

      this.method2775();
   }

   @ObfuscatedName("i")
   public void method2759() {
      for(int var1 = 0; var1 < this.verticesCount; ++var1) {
         this.verticesX[var1] = -this.verticesX[var1];
         this.verticesZ[var1] = -this.verticesZ[var1];
      }

      this.method2775();
   }

   @ObfuscatedName("j")
   void method2776() {
      if(!this.isBoundsCalculated) {
         super.height = 0;
         this.field1634 = 0;
         this.field1635 = 999999;
         this.field1604 = -999999;
         this.field1637 = -99999;
         this.field1642 = 99999;

         for(int var1 = 0; var1 < this.verticesCount; ++var1) {
            int var2 = this.verticesX[var1];
            int var3 = this.verticesY[var1];
            int var4 = this.verticesZ[var1];
            if(var2 < this.field1635) {
               this.field1635 = var2;
            }

            if(var2 > this.field1604) {
               this.field1604 = var2;
            }

            if(var4 < this.field1642) {
               this.field1642 = var4;
            }

            if(var4 > this.field1637) {
               this.field1637 = var4;
            }

            if(-var3 > super.height) {
               super.height = -var3;
            }

            if(var3 > this.field1634) {
               this.field1634 = var3;
            }
         }

         this.isBoundsCalculated = true;
      }
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
           signature = "(IIIII)Ldh;"
   )
   public final Model method2778(int var1, int var2, int var3, int var4, int var5) {
      ViewportMouse.client.getLogger().trace("Lighting model {}", this);
      Model var6 = this.copy$toModel(var1, var2, var3, var4, var5);
      if(var6 == null) {
         return null;
      } else {
         if(this.faceTextureUCoordinates == null) {
            this.computeTextureUVCoordinates();
         }

         RSModel var7 = (RSModel)var6;
         var7.setFaceTextureUCoordinates(this.faceTextureUCoordinates);
         var7.setFaceTextureVCoordinates(this.faceTextureVCoordinates);
         return var6;
      }
   }

   @ObfuscatedName("m")
   public void method2833() {
      for(int var1 = 0; var1 < this.verticesCount; ++var1) {
         int var2 = this.verticesX[var1];
         this.verticesX[var1] = this.verticesZ[var1];
         this.verticesZ[var1] = -var2;
      }

      this.method2775();
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
           signature = "(Lhp;II)Ldw;"
   )
   public static ModelData method2823(AbstractArchive var0, int var1, int var2) {
      byte[] var3 = var0.method4020(var1, var2, (short)-9228);
      return var3 == null?null:new ModelData(var3);
   }

   @ObfuscatedName("ac")
   static final int method2769(int var0) {
      if(var0 < 2) {
         var0 = 2;
      } else if(var0 > 126) {
         var0 = 126;
      }

      return var0;
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
           signature = "(Ldw;Ldw;IIIZ)V"
   )
   static void method2777(ModelData var0, ModelData var1, int var2, int var3, int var4, boolean var5) {
      var0.method2776();
      var0.method2774();
      var1.method2776();
      var1.method2774();
      ++field1641;
      int var6 = 0;
      int[] var7 = var1.verticesX;
      int var8 = var1.verticesCount;

      int var9;
      for(var9 = 0; var9 < var0.verticesCount; ++var9) {
         VertexNormal var10 = var0.vertexNormals[var9];
         if(var10.magnitude != 0) {
            int var11 = var0.verticesY[var9] - var3;
            if(var11 <= var1.field1634) {
               int var12 = var0.verticesX[var9] - var2;
               if(var12 >= var1.field1635 && var12 <= var1.field1604) {
                  int var13 = var0.verticesZ[var9] - var4;
                  if(var13 >= var1.field1642 && var13 <= var1.field1637) {
                     for(int var14 = 0; var14 < var8; ++var14) {
                        VertexNormal var15 = var1.vertexNormals[var14];
                        if(var12 == var7[var14] && var13 == var1.verticesZ[var14] && var11 == var1.verticesY[var14] && var15.magnitude != 0) {
                           if(var0.field1636 == null) {
                              var0.field1636 = new VertexNormal[var0.verticesCount];
                           }

                           if(var1.field1636 == null) {
                              var1.field1636 = new VertexNormal[var8];
                           }

                           VertexNormal var16 = var0.field1636[var9];
                           if(var16 == null) {
                              var16 = var0.field1636[var9] = new VertexNormal(var10);
                           }

                           VertexNormal var17 = var1.field1636[var14];
                           if(var17 == null) {
                              var17 = var1.field1636[var14] = new VertexNormal(var15);
                           }

                           var16.x += var15.x;
                           var16.y += var15.y;
                           var16.z += var15.z;
                           var16.magnitude += var15.magnitude;
                           var17.x += var10.x;
                           var17.y += var10.y;
                           var17.z += var10.z;
                           var17.magnitude += var10.magnitude;
                           ++var6;
                           field1639[var9] = field1641;
                           field1640[var14] = field1641;
                        }
                     }
                  }
               }
            }
         }
      }

      if(var6 >= 3 && var5) {
         for(var9 = 0; var9 < var0.faceCount; ++var9) {
            if(field1639[var0.indices1[var9]] == field1641 && field1639[var0.indices2[var9]] == field1641 && field1639[var0.indices3[var9]] == field1641) {
               if(var0.faceRenderTypes == null) {
                  var0.faceRenderTypes = new byte[var0.faceCount];
               }

               var0.faceRenderTypes[var9] = 2;
            }
         }

         for(var9 = 0; var9 < var1.faceCount; ++var9) {
            if(field1641 == field1640[var1.indices1[var9]] && field1641 == field1640[var1.indices2[var9]] && field1641 == field1640[var1.indices3[var9]]) {
               if(var1.faceRenderTypes == null) {
                  var1.faceRenderTypes = new byte[var1.faceCount];
               }

               var1.faceRenderTypes[var9] = 2;
            }
         }

      }
   }

   @ObfuscatedName("k")
   static final int method2779(int var0, int var1) {
      var1 = (var0 & 127) * var1 >> 7;
      if(var1 < 2) {
         var1 = 2;
      } else if(var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }
}