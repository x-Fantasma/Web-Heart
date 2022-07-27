package co.fanti.command;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommandResponse<V> {

    private V value;
}
