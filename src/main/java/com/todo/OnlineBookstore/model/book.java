package com.todo.OnlineBookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@Getter
@Setter

public class book {
    private int id;
    private String title;
    private String author;
    private double price;
}
