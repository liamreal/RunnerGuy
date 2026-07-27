package com.liamreal.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.liamreal.view.Viewer;
import org.junit.jupiter.api.Test;

public class GameLoopTest {
    @Test
    public void testTickUpdatesGameAndView() {
        Model world = mock(Model.class);
        Viewer viewer = mock(Viewer.class);

        when(world.update()).thenReturn(false);

        GameLoop loop = new GameLoop(world, viewer);

        boolean complete = loop.tick();

        assertFalse(complete);

        verify(world).update();
        verify(viewer).updateView();
    }

    @Test
    public void testInitialiseCreatesInitialEntities() {
        Model world = new Model();
        Viewer viewer = new Viewer(world);

        GameLoop loop = new GameLoop(world, viewer);

        assertEquals(
            0, world.getEntityManager().getEntityMap().size()
        );

        loop.initialiseGame();

        assertTrue(
            world.getEntityManager().getEntityMap().size() > 0
        );
    }
}