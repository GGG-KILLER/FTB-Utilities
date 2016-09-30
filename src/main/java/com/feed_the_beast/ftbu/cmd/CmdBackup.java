package com.feed_the_beast.ftbu.cmd;

import com.feed_the_beast.ftbl.lib.BroadcastSender;
import com.feed_the_beast.ftbl.lib.cmd.CommandLM;
import com.feed_the_beast.ftbl.lib.util.LMFileUtils;
import com.feed_the_beast.ftbu.api.FTBULang;
import com.feed_the_beast.ftbu.config.FTBUConfigBackups;
import com.feed_the_beast.ftbu.world.backups.Backups;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

public class CmdBackup extends CommandTreeBase
{
    public static class CmdBackupStart extends CommandLM
    {
        public CmdBackupStart()
        {
            super("start");
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender ics, String[] args) throws CommandException
        {
            boolean b = Backups.INSTANCE.run(ics);
            if(b)
            {
                FTBULang.BACKUP_MANUAL_LAUNCH.printChat(BroadcastSender.INSTANCE, ics.getName());

                if(!FTBUConfigBackups.USE_SEPARATE_THREAD.getBoolean())
                {
                    Backups.INSTANCE.postBackup();
                }
            }
            else
            {
                FTBULang.BACKUP_ALREADY_RUNNING.printChat(ics);
            }
        }
    }

    public static class CmdBackupStop extends CommandLM
    {
        public CmdBackupStop()
        {
            super("stop");
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender ics, String[] args) throws CommandException
        {
            if(Backups.INSTANCE.thread != null)
            {
                Backups.INSTANCE.thread.interrupt();
                Backups.INSTANCE.thread = null;
                FTBULang.BACKUP_STOP.printChat(ics);
                return;
            }

            throw FTBULang.BACKUP_NOT_RUNNING.commandError();
        }
    }

    public static class CmdBackupGetSize extends CommandLM
    {
        public CmdBackupGetSize()
        {
            super("getsize");
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender ics, String[] args) throws CommandException
        {
            String sizeW = LMFileUtils.getSizeS(ics.getEntityWorld().getSaveHandler().getWorldDirectory());
            String sizeT = LMFileUtils.getSizeS(Backups.INSTANCE.backupsFolder);
            FTBULang.BACKUP_SIZE.printChat(ics, sizeW, sizeT);
        }
    }

    public CmdBackup()
    {
        addSubcommand(new CmdBackupStart());
        addSubcommand(new CmdBackupStop());
        addSubcommand(new CmdBackupGetSize());
    }

    @Override
    public String getCommandName()
    {
        return "backup";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "command.ftb.backup.usage";
    }
}