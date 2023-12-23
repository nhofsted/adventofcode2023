package model;

public class Button {
    public Signal[] press() {
        return new Signal[] {new Signal("button", "broadcaster", false)};
    }
}