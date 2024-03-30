# Divinity Unbound

---

## Mod Description

Divinity Unbound is a magic mod that adds new armor, weapons, and tools forged from magical materials. 
Utility blocks and items are also crafted from magical materials; some of these utility blocks include ways to summon 
particular types of mobs or magically grab all the items floating in an area and automatically deposit in a below inventory. 
If magical utility items are your preference, maybe you want to get back to your base quicker with the Wand of Teleportation 
or maybe you want to shoot magical arrows with the Wand of The Archer.

---

## Getting Started

Venture into the world of minecraft and search for the elusive magic ore Celestite to get started.

Once you've found some ore and smelted it into Celestite, 
you can put it towards some tools, armor, 
or even a Chronos Time Accumulator.

A Chronos Time Accumulator will magically accumulate and distill grains of time 
as real time passes. You'll have to make some celestite infused stone and some 
golden celestite infused stone to make one but it'll be worth it.


---

## Next Steps

Now that you're magically distilling grains of time, you should be able to transmute 
it into other usable materials.

Some Celestium Dust and some Unholy Dust would be the next step towards 
controlling the divine, the unholy, space, and time.

Rumor is that Celestium Dust can only be obtained if a Grain of Time is used
on a magical golden block *very close to the gods on high*.

Unholy Dust however can only be obtained if a Grain of Time is 
used on an indestructible block in the underworld.

From here, you should be able to start dabbling into the more powerful 
magical items if you can obtain some Time Forged Ingots.

*Note: You can craft grains of time into sand of time to be able to convert 9 at a time.*

---

## "The End" Game

At this point you have some experience with magic and time but 
now I think it is time to master space as well.

If only I knew how to get to _the end_ of the road, I could try to 
use my Grains of Time on some Obsidian. I've heard that 
Obsidian has magical properties. The way it forms with lava and water 
is akin to the relationship of space and time.

If I could only figure out how to make this Space Dust I've read about 
then I would be able to make some Space Forged Ingots too.

---

## Blocks

#### Mob Utility Blocks

Divine Replicator summons copies of a mob that has been captured 
by a Wand of Capturing. Redstone Toggleable.

Space Siphon pulls in items within a radius around it and
pushes them to the inventory below. Redstone Toggleable.

Unholy Silencer - Attacks mobs within a radius around it. Utilizes sword in inventory to calculate damage. Redstone Toggleable.
(Does take into account sword enchantments, e.g. Looting, Sharpness, etc)

Unholy Grass Block - Randomly spawns mobs every 1-10 seconds, only spawns mobs below a light level of 8.

Knowledge Extractor - Extracts xp from player when standing on. Use a redstone signal to dispense stored xp. **When the Knowledge Extractor is not receiving a redstone signal it will retrieve all experience orbs within a 5 block radius and store them.**

Celestial Glass - Allows players to walk through but mobs cannot. Hold shift to stand on.

Dark Celestial Glass - Allows players to walk through but mobs cannot. Hold shift to stand on. **Does not allow light to pass through.**

Withered Glass - Wither, Dragon, and Explosion proof glass.

Mob Attractor - Pulls mobs towards itself. Redstone Toggleable.

#### Farming Blocks

Demeter's Harvester - Needs power and a hoe to run. Will plant seeds from internal inventory on farmland in a radius around the block. 
Will harvest fully grown plants, but leaves drops on the ground. Recommended to use Space Siphon with some range and speed upgrades to retrieve crops and seeds.

Persephone's Blessing - Randomly applies a growth tick to crops in a radius around the block.

#### Magical Blocks

Chronos Time Accumulator magically distills Grains of Time.

Generation Station generates resources depending on the foci placed within.

Mystic Chronograph creates a localized time space bubble and can 
drastically speeds up the block below it.

Space Time Evaporator - Evaporates space time on to coal, causing it to recrystalize and form space fuel. Produces energy.

Space Time Amalgamator - Combines Celestium Dust, Unholy Dust, and Space Dust to create 
Liquid Space Time. Needs power.

Wormhole Transporter - Creates a localized wormhole that is linked to another transporter. Can move Items, Liquids, and Energy.
Use an Import or Export card to link to another wormhole.

Coal Generator - Basic generator that takes coal to create power.

Hallowed Fluid Tank - Can store fluids. Can be moved and retains inventory.

Zeus' Battery - Can store energy. Can be moved and retains inventory.

Item Trashcan - Voids any item inserted into it

Fluid Trashcan - Voids any fluid inserted into it

Energy Trashcan - Voids any energy inserted into it

### Magical Multiblocks

Proteus Converter - Converts Grains of Time into the respective dusts automatically depending on what dimension it is in.

To build the Proteus Converter, you will need: 
- 8x Wildersung Logs, 4x Polished Blackstone, 4x Frozen Time Glass, 
4x Time Forged Blocks, 4x Frozen Time Lamps, 1x Space Forged Block, 1x Proteus Controller

It is a 3x3x3 multiblock structure you will have to build. From the bottom up the structure is built like so:

**NOTE: THE FINAL BLOCK YOU HAVE TO PLACE IS THE PROTEUS CONTROLLER TO MAKE THE MULTIBLOCK FORM**
```
From a top down view:
Bottom Layer:
WBW
BSB
WBW

Middle Layer:
TGT
G G
TGT

Top Layer:
WLW
LPL
WLW

W - Wildersung Logs
B - Polished Blackstone
S - Space Forged Block
T - Time Forged Block
G - Frozen Time Glass
L - Frozen Time Lamp
P - Proteus Controller
```

### Wormhole Transporter

Simple example using the Wormhole Transporter:
You have 2 chests that you want to move items between across some distance.

- Place a Wormhole Transporter on each chest.
- Take an Import Card and Shift+Right Click **on the Wormhole Transporter you want to take from** to save the coordinates to the card.
- Place the Import Card with the saved coords **into the Wormhole Transporter you want to move items to.**
- Place a **blank Export Card into the Wormhole Transporter you want to take from** to start extracting items.

Items are now being extracted into the first Wormhole Transporter with the blank Export Card, then they are automatically pulled into the
Wormhole Transporter with the linked Import Card, and finally the Transporter with the linked Import Card is pushing into the chest it is placed on.

You can achieve the same goal by using a linked Export Card and a blank Import Card instead. 

**You only need 1 linked card within the system**

A linked Export Card will _pull_ from the neighboring block and _push_ to the linked Wormhole Transporter.

A linked Import Card will _push_ from the neighboring block and _pull_ from the linked Wormhole Transporter.

### Block Upgrades

There are currently speed and quantity upgrades available 
to enhance the block they are placed around.

You are able to place upgrades in the 8 blocks (on the same Y level) 
surrounding the block you are upgrading.

|                          | Speed Upgrade | Quantity Upgrade | Range Upgrade |
|--------------------------|---------------|------------------|---------------|
| Chronos Time Accumulator | Y             | Y                | N             |
| Divine Replicator        | Y             | Y                | N             |
| Generation Station       | Y             | Y                | N             |
| Mystic Chronograph       | N             | N                | N             |
| Space Siphon             | Y             | N                | Y             |
| Unholy Silencer          | Y             | N                | N             |
| Space Time Evaporator    | N             | N                | N             |
| Space Time Amalgamator   | N             | N                | N             |
| Wormhole Transporter     | N             | N                | N             |
| Mob Attractor            | Y             | N                | Y             |
| Demeter's Harvest        | Y             | N                | Y             |
| Persephone's Blessing    | Y             | N                | Y             |

---

### Wands

Unholy Wand pulls ores from underground to the surface.

Wand of Capturing captures a mob it is used on, 
can use the wand on a block to release the mob. If 
you have a captured mob in the wand, place in the 
Divine Replicator to summon more of them!

Wand of Respiration provides water breathing and dolphin's 
grace on use.

Wand of Teleportation can be shift right clicked on a block 
to save a location (check the item's tooltip to see the 
saved coordinates). Once a position has been saved, you can 
use the wand to teleport to the saved position. Shift right 
click in the air to clear the saved block position or shift 
right click on a different block to change the saved position.

Divine Wand of Flight provides 1 minute of creative flight on 
use.

Wand of The Archer shoots spectral arrows and can be enchanted with bow enchantments.

Wand of the Celebration shoots firework rockets (duration 3).

Wand of Fire Bending summons temporary lava in a plus pattern and provides Fire Resistance. Can be used on mobs and blocks.

Wand of Magnetization - Pulls items within a 5 block radius to you when active in your inventory.

Wand of Healing - Heals you for a couple hearts and gives Regeneration and Absorption for a short period of time.

Greater Wand of Healing - Heals you for several hearts and gives Regeneration for a short period of time. Provides Absorption, Resistance, and Fire Resistance for a moderate amount of time.

---

### Enchantments

Bane of Passives - Instantly kills passive mobs.

Prosperity - Provides additional chance of rolling fortune drops.

> Prosperity I gives a 33% (1⁄3) chance to multiply drops by 2 (averaging 33% (1⁄3) increase),
Prosperity II gives a chance to multiply drops by 2 or 3 (25% (1⁄4) chance each, averaging 75% (3⁄4) increase), 
and Prosperity III gives a chance to multiply drops by 2, 3, or 4 (20% (1⁄5) chance each, averaging 120% (11⁄5) increase).
> Prosperity III gives 2.2x (21⁄5) drops on average.

#### Example runs Fortune III vs Fortune III + Prosperity III

| Runs                               | 1  | 2  | 3  | 4  | 5  |
|------------------------------------|----|----|----|----|----|
| Fortune III                        | 26 | 18 | 18 | 19 | 24 |
| Fortune III<br/>and Prosperity III | 43 | 29 | 38 | 37 | 55 |


---

### Armor and Tools

Materials:
Celestite, Time Forged Ingots, Space Forged Ingots, Space Time Ingots

Full sets of tools and armor for each material type. There are also Paxels!

Paxels also have the unique ability to set a max mining speed when holding shift and mining. For example, 
if you have efficiency V and haste II you may accidentally insta-mine 5 blocks in a line. If you hold shift when 
mining it will cap how fast you can mine. This can be incredibly useful when building if you don't want to accidentally mine a bunch of blocks.

Full sets of armor have different full set bonuses!

---

#### TODO:
- [X] Range Upgrade
- [ ] Efficiency Upgrade
- [ ] Mystic Chronograph supporting upgrades
- [ ] Space Time Amalgamator supporting upgrades
- [ ] Space Time Evaporator supporting upgrades
- [X] Space Time Tools
- [ ] Space Time Armor
- [X] Paxels
- [X] Food items
- [X] More Wands
- [X] Space Time Fluid
- [ ] Linked chests with filters
- [ ] Processing type machines for automation

#### Nice to have TODO:
- [ ] Particles for Space Siphon
- [ ] Particles for Divine Replicator
- [ ] Particles for Mystic Chronograph
- [ ] Particles for Unholy Silencer
- [ ] Particles for Space Time Evaporator
- [ ] Animated Models for blocks