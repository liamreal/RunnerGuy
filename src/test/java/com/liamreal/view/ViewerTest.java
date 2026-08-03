package com.liamreal.view;

import java.awt.image.BufferedImage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import com.liamreal.user.Config;
import com.liamreal.game.Model;
import java.awt.Dimension;
import java.awt.Graphics;


public class ViewerTest {
    private MockedStatic<Config> mockedConfig;
    private Config mockConfig;

    @BeforeEach
    public void setup() {
        mockConfig = mock(Config.class);

        mockedConfig = mockStatic(Config.class);

        mockedConfig.when(Config::getInstance)
                    .thenReturn(mockConfig);

        when(mockConfig.getResolutionX())
                .thenReturn(1280);

        when(mockConfig.getResolutionY())
                .thenReturn(720);
    }

    @AfterEach
    public void tearDown() {
        mockedConfig.close();
    }

    @Test
    public void testPreferredSize() {
        Model world = new Model();

        Viewer viewer = new Viewer(world);

        Dimension size = viewer.getPreferredSize();

        assertEquals(1280, size.width);
        assertEquals(720, size.height);
    }

    @Test
    public void testUpdateViewRepaints() {
        Model world = new Model();

        Viewer viewer = spy(new Viewer(world));

        viewer.updateView();

        verify(viewer).repaint();
    }

    @Test
    public void testPaintComponentWithNoEntities() {
        Model world = new Model();

        Viewer viewer = new Viewer(world);

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics(); // using real graphics for random image to test painting with no entities works just fine

        assertDoesNotThrow(() -> viewer.paintComponent(graphics));
    }
}
