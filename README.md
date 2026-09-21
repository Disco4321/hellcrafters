# Hellcrafters
 A Helldivers 2 inspired minecraft mod. 
 My goal is to emulate the mechanics and functions of several key asepcts of the game Helldivers 2.
 This is not an affiliated project, merely a fanmade recreation of several game design concepts.
## Goals
The current scope of the project is as follows:
- [x] Creating custom Minecraft entities with multiple hitboxes
  - KnightLib allows definition of subhitboxes
- [x] Creating custom hitboxes that follow animations
  - KnightLib allows us to sync Geckolib bones to hitboxes
- [x] Creating custom hitboxes unbound by Minecraft's harsh AABB system
  - KnightLib allows us to define OBB hitboxes, that can translate and rotate
- [ ] Registering custom collision detection between custom hitboxes and vanilla projectiles 
- [x] Registering custom collision detection between custom hitboxes and a gun mod
- [x] Implementing Helldivers 2 weaponry within the gun mod.
- [ ] Customizing weaponry values to recreate the armor penetration system
- [ ] Defining damage amount based off weapon penetration
- [ ] Defining damage amount based off armor value of specific hitbox

<br>

## Methodology
This section will be the nitty-gritty science.
I'll explain each goal in detail, and the how I've managed to accomplish it

#### Multi-Hitbox Entities
Helldivers 2 imo is defined by it's craft enemy design.
Every enemy has several hitboxes, with different attributes for each piece.
Vanilla Minecraft has several clunky ways of dealing with this, but due to my inexperience I really wanted a third party helper mod to give me a hand.
This took a lot of testing, trial and error.
After about a month of rigorously testing several different mods, I found [KnightLib](https://www.curseforge.com/minecraft/mc-mods/knight-lib) to have the most sophisticated and well-designed implementation.
While not advertising itself as a multi-hitbox entity mod, it actually has a minor feature where the server can define OBB hurtboxes synced to Geckolib animation bones with their own built-in entity collision detection.

This might seem like a silver bullet that solves all our problems, but it actually presents us with several new ones.
For one, [Minecraft projectiles](https://minecraft.wiki/w/Projectile#Entity_collision) do not check whether they're inside another entity's hitbox every tick.
They inflate their own AABB hitbox so the initial coords match the starting position, and the ending coords match the final position of the distance they'll move through that tick (the delta movement value).
Then check EVERY entity within that area for a raycast collision.
This means collision checks start from the bullet's tick function, and if the bullet doesn't recognize KnightLib's OBB hitboxes as hitboxes, then no interaction can occur.
Which brings us to the next section.

### Guns
Helldivers 2 wouldn't be the game it is without it's vast array of unique guns.
And it's my goal to recreate a lot of that.
Luckily, Minecraft gun mods are very polished in this day and age of Minecraft modding.
Unluckily, I have no idea where to start coding a mixin into an already made gun mod that dynamically calculates per-part collision on an entity's subdivided hitboxes who moves according to a GeckoLib animation which is apparently only defined on the Client...
So yeah...

I attempted using [Vic's Point Blank](https://www.curseforge.com/minecraft/mc-mods/vics-point-blank) first, but the odd raytracing algorithm instead of simply creating an actual "bullet entity" confused me more than it should.
Plus it's closed source, which doesn't matter too much in the age of Java decompilation integrated into our IDEs, but yeah.
[TACZ](https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero) ended up being my mod of choice.
Open source, creates a bullet entity with a normal tick function that seems to simply replicate an arrow entity with extra steps.
Plus, I found a neat [gunpack](https://www.curseforge.com/minecraft/customization/tacz-helldivers-escalation-of-freedom) for it, that somebody has spent an immense amount of time recreating many of the Helldivers 2 weapons already, and *keeping them updated!!!*
They've got 45+ weapons already and counting, so this is a huge time saver, massive props goes out to this dedicated author.

### Collisions
Time for the backbones of the mod.
I have an abstract HellcrafterEntity to type check easily, implementing Geckolib animations and KnightLib's OBB hitboxes.
I have my dependencies, registration, and mixin IDE setup all flushed out and ready to go.
For some reason I decided to start with the gun's mod collision, but oh well.

We start with the bullet (EntityKineticBullet), which during it's onBulletTick method, expands it's AABB as described above and calls findEntityOnPath to grab a list of entities within said AABB.
Unfortunately TACZ filters out all entities that override isPickable to false, meaning we can't completely disable vanilla hitboxes, but we can work around that for now.
Also unfortunately, inflating the AABB for fast moving projectiles gives us a MASSIVE range, less than the entire level, but still a lot of extraneous entities.
TACZ filters the entities the projectile won't hit by casting a ray from the bullet's origin to endpoint, and checking which vanilla hitboxes it intersect.
We can mixin to the end of this method getHitResult, that completes that check per entity.
If the entity doesn't have sub-hitboxes, or if the entity was missed, we can skip the extra checks.
But if the prior statements were false, we can cycle through every subhitbox in the entity and call KnightLib's conveinient rayIntersects method.
Then all we have to do is sort all the collided with OBBs by distance (which KnightLib also automatically handles).
Gives us a full overview of which OBBs the bullet would pass through, sorted by distance.
Pretty exciting.

We should be able to copy this idea over to Minecraft's vanilla projectile logic, as it seems TACZ used that as a base.
