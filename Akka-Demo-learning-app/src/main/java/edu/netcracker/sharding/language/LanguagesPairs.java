package edu.netcracker.sharding.language;

import lombok.Getter;

import static edu.netcracker.sharding.language.Languages.EN;
import static edu.netcracker.sharding.language.Languages.RU;

/**
 * @author svku0919
 * @version 12.08.2020
 */
public enum LanguagesPairs {
    EN_RU(EN, RU),
    RU_EN(RU, EN);

    @Getter
    final Languages first;
    @Getter
    final Languages second;

    LanguagesPairs(Languages first, Languages second) {
        this.first = first;
        this.second = second;
    }
}
