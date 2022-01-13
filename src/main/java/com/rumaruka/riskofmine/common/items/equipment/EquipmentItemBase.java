package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;

public class EquipmentItemBase extends ItemCollectiblesBase {
    private final CategoryEnum categoryEnum;

    public int cooldownMinus;

    public EquipmentItemBase(CategoryEnum categoryEnum) {
        super(EnumType.EQUIPMENT, categoryEnum, 1);
        this.categoryEnum = categoryEnum;

    }


}
