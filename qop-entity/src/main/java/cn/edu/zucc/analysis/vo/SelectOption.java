package cn.edu.zucc.analysis.vo;

import lombok.Data;

/**
 * @author Bruce
 * @since 05-17-2021
 **/
@Data
public class SelectOption {
    private Integer selectedCount;
    private String text;
    private Double score;
    private String label;
    private Integer value;
}
