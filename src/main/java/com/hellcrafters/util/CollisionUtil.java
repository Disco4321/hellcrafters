package com.hellcrafters.util;

import com.hellcrafters.entity.HellcrafterEntity;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitbox;
import dev.xylonity.knightlib.api.entity.hitbox.BoneHitboxHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollisionUtil {

    /**
     * This is a helper method designed to take a ray travelling through an entity's vanilla
     * AABB, check for collisions between the ray and a custom entity's OBBs, then order the list
     * from closest to farthest. Huge help
     * @apiNote OBB hitboxes do not exist client-side
     * @param entity   The entity the vector travels through
     * @param startVec The initial coordinates of the vector in global space
     * @param endVec   The final coordinates of the vector in global space
     * @return Returns a sorted list of the custom OBB hitboxes the ray travels through
     */
    public static List<Map.Entry<BoneHitbox, Double>> getSortedRaycast(Entity entity, Vec3 startVec, Vec3 endVec) {

        // safely grabs a bonehitbox collection through a helper method
        Collection<BoneHitbox> boneHitboxes = getBoneHitboxCollection(entity);

        // streams are pretty nifty
        return boneHitboxes.stream()
                // converts the boneHitboxes to a Pair of boneHitbox and a double representing a detected collision and distance
                .map(boneHitbox -> Map.entry(boneHitbox, (boneHitbox.getCurrentOBB() != null ? boneHitbox.getCurrentOBB().rayIntersects(startVec, endVec) : -2)))
                // filters out all the misses, as rayIntersect returns -1 if it's a miss
                .filter(boneHitboxDoubleEntry -> boneHitboxDoubleEntry.getValue() != -1d)
                // Map.Entry provides a beautiful comparator to help sort these types of Pairs based off Value
                .sorted(Map.Entry.comparingByValue())
                .toList();
    }


    /**
     * This helper method, similarly to the one above, loops through every OBB within an entity, and calculates
     * whether the given ray travels through one of them. Designed to be slightly faster than the sorted version,
     * due to returning earlier
     * @apiNote OBB hitboxes do not exist client-side
     * @param entity The entity the segment travels through
     * @param startVec The starting global coordinates of the line segment
     * @param endVec The ending global coordinates of the line segment
     * @return Returns a boolean whether the ray travels through one of the OBBs
     */
    public static Boolean getUnsortedRaycast(Entity entity, Vec3 startVec, Vec3 endVec) {

        // safely grabs a bonehitbox collection through a helper method
        Collection<BoneHitbox> boneHitboxes = getBoneHitboxCollection(entity);

        // returns as soon as the stream hits a boneHitbox that collides with the ray
        return boneHitboxes.stream().anyMatch(boneHitbox ->
                (boneHitbox.getCurrentOBB() != null && boneHitbox.getCurrentOBB().rayIntersects(startVec, endVec) != -1));
    }


    /**
     * This method runs the "required" sanity checks to grab the entire BoneHitbox collection from an entity
     * @apiNote Currently requires entity to extend HellcrafterEntity
     * @param entity The entity in question
     * @return Returns a Java Collection containing every BoneHitbox. Very easy to manipulate
     */
    public static Collection<BoneHitbox> getBoneHitboxCollection(Entity entity) {
        // OBB hitboxes don't exist on the client, won't crash but messes me up
        if (entity.level().isClientSide)
            throw new RuntimeException("Bone Hitboxes are only stored server-side!");

        // returns early if the entity was invalid for our use cases
        if (!(entity instanceof HellcrafterEntity))
            throw new RuntimeException("Entity "+entity.getName()+" is not a Hellcrafter entity!");

        // snags the boneHitboxes. Every HellcrafterEntity will have a hitboxManager, so the warning is irrelevant
        Collection<BoneHitbox> boneHitboxes = ((BoneHitboxHolder) entity).getBoneHitboxManager().getAll();

        // ensures there were boneHitboxes within that entity. May remove later
        if (boneHitboxes.isEmpty())
            throw new RuntimeException("Entity "+entity.getName()+" has no bone hitboxes!");

        return boneHitboxes;
    }
}