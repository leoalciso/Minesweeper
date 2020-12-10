package controller;


import java.io.Serializable;

/**
 * Message interface that represents message (event) sent from View to Model. Implemented by BaseMessage and FlagMessage clases. Other Message classes act as leaves in Composite Design Pattern.
 */
public interface Message extends Serializable {
    Pair<Integer, Integer> getEvent();
}

