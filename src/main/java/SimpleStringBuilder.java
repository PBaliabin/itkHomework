import java.util.Stack;

public class SimpleStringBuilder {
    private StringBuilder stringBuilder = new StringBuilder();
    private Stack<String> snapshots = new Stack<>();

    public SimpleStringBuilder append(String s) {
        snapshots.push(stringBuilder.toString());
        stringBuilder.append(s);

        return this;
    }

    public SimpleStringBuilder undo() {
        stringBuilder = new StringBuilder(snapshots.pop());

        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
