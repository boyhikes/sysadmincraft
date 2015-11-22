package pw.ian.sysadmincraft.system

import sys.process._

case class Process(id: Int, realMemory: Long, virtualMemory: Long, name: String) {

   def totalMemory = realMemory + virtualMemory

}

object ProcessAdmin {

   def processes: Map[Int, Process] = {
      Map()
   }

   def kill(processId: Int) = {

   }

   private def findUserProcesses {
      val rawProcessOutput = "ps axco user,pid,rss,vsz,command" !!

      rawProcessOutput.split('\n').tail.map(_.split(' ').filter(!_.isEmpty))
         .filter(!_ (0).startsWith("root"))
         .filter(!_ (0).startsWith("_"))
         .map(_.tail).toList.map(_.toList)
   }
}
