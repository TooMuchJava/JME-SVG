/*
 * Copyright 2019 Indigo Amann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lazydev.jme.svg;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Picture;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.image.ColorSpace;
import com.jme3.util.BufferUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Android_SVGUtil implements SVGUtil {
    
    public SVG getSVG(InputStream stream) throws SVGParseException {
        return SVG.getFromInputStream(stream);
    }
    
    public Bitmap getBitmap(SVG image, int width, int height) {
        width = 500;
        height = 500;
        image.setDocumentWidth(width);
        image.setDocumentHeight(height);
        Picture picture = image.renderToPicture();
        Bitmap b = Bitmap.createBitmap(picture).copy(Bitmap.Config.ARGB_8888, true);
        
        return b;
    }

    @Override
    public Image createJMEImage(InputStream stream, int width, int height, SVGTextureKey key) throws Exception {
        return bitmapToImage(getBitmap(getSVG(stream), width, height), key == null ? true : key.isFlipY());
    }
    /**From AndroidBufferImageLoader */
    public Image bitmapToImage(Bitmap bitmap, boolean flip) throws IOException {
        Options options = new Options();
        options.inPreferQualityOverSpeed = false;
        options.inPreferredConfig = Config.RGB_565;
        options.inScaled = false;
        options.inDither = false;
        options.inInputShareable = true;
        options.inPurgeable = true;
        options.inSampleSize = 1;

        Format format = Format.RGBA8;
        byte bpp = 4;
        //TextureKey texKey = (TextureKey)assetInfo.getKey();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        ByteBuffer data = BufferUtils.createByteBuffer(bitmap.getWidth() * bitmap.getHeight() * bpp);
        if (format == Format.RGBA8) {
            int[] pixelData = new int[width * height];
            bitmap.getPixels(pixelData, 0, width, 0, 0, width, height);
            if (/*texKey.isFlipY()*/true) {
                int[] sln = new int[width];

                for(int y1 = 0; y1 < height / 2; ++y1) {
                    int y2 = height - y1 - 1;
                    convertARGBtoABGR(pixelData, y1 * width, sln, 0, width);
                    convertARGBtoABGR(pixelData, y2 * width, pixelData, y1 * width, width);
                    System.arraycopy(sln, 0, pixelData, y2 * width, width);
                }
            } else {
                convertARGBtoABGR(pixelData, 0, pixelData, 0, pixelData.length);
            }

            data.asIntBuffer().put(pixelData);
        } else {
            if (/*texKey.isFlipY()*/true) {
                Matrix flipMat = new Matrix();
                flipMat.preScale(1.0F, -1.0F);
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), flipMat, false);
                bitmap.recycle();
                bitmap = newBitmap;
                if (newBitmap == null) {
                    throw new IOException("Failed to flip image: <SVG image, check Android_SVGUtil for errors>");
                }
            }

            bitmap.copyPixelsToBuffer(data);
        }

        data.flip();
        bitmap.recycle();
        Image image = new Image(format, width, height, data, ColorSpace.sRGB);
        return image;
    }
    /**From AndroidBufferImageLoader */
    private static void convertARGBtoABGR(int[] src, int srcOff, int[] dst, int dstOff, int length) {
        for (int i = 0; i < length; i++) {
            int argb = src[srcOff + i];
            int a = (argb & 0xFF000000);
            int b = (argb & 0x000000FF) << 16;
            int g = (argb & 0x0000FF00);
            int r = (argb & 0x00FF0000) >> 16;
            int abgr = a | b | g | r;
            dst[dstOff + i] = abgr;
        }
    }
    
}
