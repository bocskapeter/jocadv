package eu.bopet.jocadv.core.constraints;

public abstract class Const {

    public static final short SYSTEM = 0;
    public static final short USER = 1;

    private static final String[] symbols = {"🖳", "🖮"};

    private short type;

    public Const() {
        super();
    }

    public void setType(short type) {
        this.type = type;

    }

    public short getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return symbols[type];
    }
}
