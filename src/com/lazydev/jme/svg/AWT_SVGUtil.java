/*
 * The MIT License
 *
 * Copyright 2019 Indigo Amann.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.lazydev.jme.svg;

import com.jme3.texture.Image;
import com.jme3.texture.plugins.AWTLoader;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class AWT_SVGUtil implements SVGUtil {
    protected AWTLoader loader;
    int svgn = 0;
    public AWT_SVGUtil() {
        loader = new AWTLoader();
    }
    public BufferedImage createBufferedImage(InputStream stream, int width, int height) throws IOException, TranscoderException {
        PNGTranscoder t = new PNGTranscoder();
        t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(width));
        t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(height));
        TranscoderInput input = new TranscoderInput(stream);
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(ostream);
        t.transcode(input, output);
        ByteArrayInputStream instream = new ByteArrayInputStream(ostream.toByteArray());
        
        return ImageIO.read(instream);
    }
    public Image createJMEImage(BufferedImage image) {
        //ByteBuffer buf = ByteBuffer.allocateDirect(image.getHeight() * image.getWidth() * 4);
        //ImageToAwt.convert(image, Image.Format.ABGR8, buf);
        return loader.load(image, true);//new Image(Image.Format.ABGR8, 640, 480, buf);
    }
    @Override
    public Image createJMEImage(InputStream stream, int width, int height, SVGTextureKey key) throws IOException, TranscoderException {
        return createJMEImage(createBufferedImage(stream, width, height));
    }
}
