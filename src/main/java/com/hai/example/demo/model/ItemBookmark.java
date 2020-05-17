package com.hai.example.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hai.example.demo.repository.ItemBookmarkRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Data
@NoArgsConstructor
@RedisHash("ItemBookmark")
public class ItemBookmark implements Serializable {
    @Id
    private String Id;

    private Integer sbmItemId = 0;
    private Integer easyId = 0;
    private Integer shopId = 0;
    private Long itemId = 0L;
    private Integer listId = 0;
    private String itemUrl = "";
    private Integer itemPrice = 0;
    private Integer itemType = 0;
    private String regTs = "";
    private String updateRegTs = "";
    private String sbmItemComment = "";
}
