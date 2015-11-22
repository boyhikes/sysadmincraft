package pw.ian.sysadmincraft.world

import org.bukkit.Material
import org.bukkit.block.Block
import pw.ian.sysadmincraft.Process
import pw.ian.sysadmincraft.world.WorldConstants._

case class ProcessPillar(base: Block, var process: Process) {

  var height = 0
  update(process)

  def update(process: Process) = {
    assert(this.process.id == process.id)
    val newHeight = memToHeight(process.memoryUsage)
    form(newHeight)
    this.process = process
    this.height = newHeight
  }

  private def memToHeight(memoryUsage: Long) = {
    Math.max(WorldConstants.MAX_HEIGHT,
      ((memoryUsage.toDouble / MAX_MEMORY) * MAX_HEIGHT).toInt)
  }

  private def form(newHeight: Int): Unit = {
    if (newHeight > height) {
      PillarUtil.construct(base, height + 1, newHeight, Material.GOLD_BLOCK)
    } else {
      PillarUtil.destruct(base, newHeight + 1, height)
    }
  }

}

object PillarUtil {

  def construct(base: Block, startHeight: Int, endHeight: Int, blockType: Material): Unit =
    blocks(base, startHeight, endHeight).foreach(_.setType(blockType))

  def destruct(base: Block, startHeight: Int, endHeight: Int): Unit =
    blocks(base, startHeight, endHeight).foreach(_.setType(Material.AIR))

  def blocks(base: Block, startHeight: Int, endHeight: Int): IndexedSeq[Block] =
    for {
      level <- startHeight to endHeight
      x <- base.getX to base.getX + PILLAR_WIDTH
      z <- base.getZ to base.getZ + PILLAR_WIDTH
    } yield base.getWorld.getBlockAt(x, level + START_HEIGHT, z)

}
