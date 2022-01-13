package com.rumaruka.riskofmine.client;

import com.rumaruka.riskofmine.client.render.HealthOrbRenderer;
import com.rumaruka.riskofmine.init.ROMEntitys;
import net.minecraft.client.renderer.entity.EntityRenderers;



public class ROMEntityRegister {


    public static void renderEntity() {
        EntityRenderers.register(ROMEntitys.HEALTH_ORB, HealthOrbRenderer::new);
    }

}
