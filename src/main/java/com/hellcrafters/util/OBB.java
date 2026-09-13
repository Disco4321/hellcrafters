package com.hellcrafters.util;

import org.joml.Vector3d;
import software.bernie.geckolib.animation.state.BoneSnapshot;

public class OBB {
    // scale, rotation, rotation
    // public final Vec3 xAxis;
    // public final Vec3 yAxis;
    // public final Vec3 zAxis;

    // center, half-widths, rotation matrix
    public final Vector3d center;
    //public final Vec3 extents;
    //public final int[][] matrix = new int[3][3];

    // corners = center +- (extents[0]*matrix[0]) +- (extents[1]*matrix[1]) +- (extents[2]*matrix[2])

/*
    public OBB(AABB aabb) {
        center = aabb.getCenter();
        extents =
    }*/

    public OBB(BoneSnapshot boneSnapshot) {
        center = boneSnapshot.getBone().getWorldPosition();

    }
}
/*
    public OBB(Bone bone) {
        bone.rotation().
    }




}
*/