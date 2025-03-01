package com.aws.carepoint.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FoodList { // 공공데이터 api용 domain
    private int foodListPk;
    private String menu;
    private float protein;
    private float carbohydrate;
    private float fat;
    private int kcal;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private int foodPk;
    private int amount;
    private String servingSize; // 1회 제공량


    // JSON 변환을 위한 포맷 지정
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate selectDate;
    private String foodType; // 아침(B), 점심(L), 저녁(D) 구분
    private int userPk;
}