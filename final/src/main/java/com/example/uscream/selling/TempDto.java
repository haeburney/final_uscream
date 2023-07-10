package com.example.uscream.selling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TempDto {
   private String storeid;
   private int sellproductnum;
   private int sellingcnt;
}