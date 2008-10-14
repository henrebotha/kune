package org.ourproject.kune.platf.server.manager.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.io.File;

import magick.MagickException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * UnsatisfiedLinkError: problem with jmagick installation (in debian, apt-get
 * install libjmagick6-jni, and add LD_LIBRARY_PATH=/usr/lib/jni/ to this test
 * environment params
 */
public class ImageUtilsDefaultTest {

    private static final String IMG_PATH = "src/test/java/org/ourproject/kune/platf/server/manager/impl/";
    private static String[] images = { "orig.png", "orig.gif", "orig.jpg", "orig.tiff" };
    private static String imageDest;

    @AfterClass
    public static void after() {
        File file = new File(imageDest);
        file.delete();
    }

    @BeforeClass
    public static void before() {
        imageDest = IMG_PATH + "output";
    }

    @Test
    public void generateIcon() throws MagickException {
        for (String image : images) {
            ImageUtilsDefault.createThumb(IMG_PATH + image, imageDest, 16, 16);
            Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
            assertEquals(16, (int) dimension.getHeight());
            assertEquals(16, (int) dimension.getWidth());
        }
    }

    @Test
    public void generateThumb() throws MagickException {
        for (String image : images) {
            ImageUtilsDefault.createThumb(IMG_PATH + image, imageDest, 100, 85);
            Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
            assertEquals(85, (int) dimension.getHeight());
            assertEquals(85, (int) dimension.getWidth());
        }
    }

    @Test
    public void testProportionalHigher() {
        Dimension proportionalDim = ImageUtilsDefault.calculateProportionalDim(500, 1000, 100);
        assertEquals(100, proportionalDim.width);
        assertEquals(200, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(50, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalHigherLikeSamples() {
        Dimension proportionalDim = ImageUtilsDefault.calculateProportionalDim(1200, 1600, 100);
        assertEquals(100, proportionalDim.width);
        assertEquals(133, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(16, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalHigherSame() {
        Dimension proportionalDim = ImageUtilsDefault.calculateProportionalDim(20, 100, 100);
        assertEquals(20, proportionalDim.width);
        assertEquals(100, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalHigherSmaller() {
        Dimension proportionalDim = ImageUtilsDefault.calculateProportionalDim(20, 10, 100);
        assertEquals(20, proportionalDim.width);
        assertEquals(10, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalWider() {
        Dimension proportionalDim = ImageUtilsDefault.calculateProportionalDim(1000, 500, 100);
        assertEquals(200, proportionalDim.width);
        assertEquals(100, proportionalDim.height);
        assertEquals(50, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalWiderSame() {
        Dimension proportionalDim = ImageUtilsDefault.calculateProportionalDim(100, 20, 100);
        assertEquals(100, proportionalDim.width);
        assertEquals(20, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalWiderSmaller() {
        Dimension proportionalDim = ImageUtilsDefault.calculateProportionalDim(5, 10, 100);
        assertEquals(5, proportionalDim.width);
        assertEquals(10, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testResize() throws MagickException {
        for (String image : images) {
            assertTrue(ImageUtilsDefault.scaleImage(IMG_PATH + image, imageDest, 100, 100));
            Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
            assertEquals(100, (int) dimension.getHeight());
            assertEquals(100, (int) dimension.getWidth());
        }
    }

    @Test
    public void testSize() throws MagickException {
        for (String image : images) {
            Dimension dimension = ImageUtilsDefault.getDimension(IMG_PATH + image);
            assertEquals(400, (int) dimension.getHeight());
            assertEquals(300, (int) dimension.getWidth());
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void thumbSmallerThanCropMustFail() throws MagickException {
        ImageUtilsDefault.createThumb(IMG_PATH + images[0], imageDest, 100, 200);
    }
}
