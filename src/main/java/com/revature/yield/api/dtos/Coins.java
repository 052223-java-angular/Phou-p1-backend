package com.revature.yield.api.dtos;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Coins {

    /* todo -> https://api.coingecko.com/api/v3/coins/list
     * this will retrieve the current list of supported assets
     * */
    private String id;
    private String symbol;
    private String name;

}
