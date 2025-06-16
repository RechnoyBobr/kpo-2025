package hse.bank.cmd;

import hse.bank.records.CommandData;


public interface Command {
    CmdResult execute(CommandData data);
}
