package cn.ichiva.luckysheet.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Serializable {
    int id;

    String doc;

    String name;

    Date time;

    String info;

}
