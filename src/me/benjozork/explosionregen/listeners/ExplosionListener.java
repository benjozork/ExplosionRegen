package me.benjozork.explosionregen.listeners;

import me.benjozork.explosionregen.ExplosionRegen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;


/**
 Looks like you decompiled my code :) Don't worry, you have to right to do so.

 The MIT License (MIT)

 Copyright (c) 2016 Benjozork

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/


public class ExplosionListener implements Listener {

    private ExplosionRegen main;
    private List<Material> blacklist = new ArrayList<>();

    public ExplosionListener(ExplosionRegen instance, List<Material> blacklistedBlocks) {
        this.main = instance;
        this.blacklist = blacklistedBlocks;
    }

    @EventHandler
    public void explosionEvent(EntityExplodeEvent e) {
        List<Block> blocks = new ArrayList<>();
        blocks.addAll(e.blockList());
        for(Iterator<Material> itm = blacklist.iterator(); itm.hasNext();) {
            Material m = itm.next();
            for(Iterator<Block> itb = blocks.iterator(); itb.hasNext();) {
                Block b = itb.next();
                if (b.getType().equals(m)) itb.remove();
            }
        }

        Collections.sort(blocks, (b1, b2) -> {
            return b1.getY() - b2.getY(); // Ascending
        });

        List<Material> materials = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        for (Block b : blocks) {
            materials.add(b.getType());
            locations.add(b.getLocation());
        }
        main.registerExplosion(materials, locations);
    }
}
