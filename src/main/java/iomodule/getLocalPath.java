package iomodule;

import com.cburch.logisim.file.LogisimFile;

public class getLocalPath {
    public String findlocalpath() {
        return LogisimFile.getMainCircuit().toString();
    }
}
