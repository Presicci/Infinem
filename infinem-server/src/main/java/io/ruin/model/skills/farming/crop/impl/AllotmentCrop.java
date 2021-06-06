package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.Crop;
import lombok.AllArgsConstructor;

import static io.ruin.cache.ItemID.COINS_995;

@AllArgsConstructor
public enum AllotmentCrop implements Crop {
	POTATO(5318, 1942, 1, 8, 9, 4, FlowerCrop.MARIGOLDS, new Item(COINS_995,  50), 6, PlayerCounter.HARVESTED_POTATO),
	ONION(5319, 1957, 5, 9.5, 10.5, 4, FlowerCrop.MARIGOLDS, new Item(COINS_995,  100), 13, PlayerCounter.HARVESTED_ONION),
	CABBAGE(5324, 1965, 7, 10, 11.5, 4, FlowerCrop.ROSEMARY, new Item(COINS_995,  200), 20, PlayerCounter.HARVESTED_CABBAGE),
	TOMATO(5322, 1982, 12, 12.5, 14, 4, FlowerCrop.MARIGOLDS, new Item(COINS_995,  300), 27, PlayerCounter.HARVESTED_TOMATO),
	SWEETCORN(5320, 5986, 20, 17, 19, 6, null, new Item(COINS_995,  500), 34, PlayerCounter.HARVESTED_SWEETCORN),
	STRAWBERRY(5323, 5504, 31, 26, 29, 6, null, new Item(COINS_995,  800), 43, PlayerCounter.HARVESTED_STRAWBERRY),
	WATERMELON(5321, 5982, 47, 48.5, 54.5, 8, FlowerCrop.NASTURTIUM, new Item(COINS_995,  5000), 52, PlayerCounter.HARVESTED_WATERMELON),
	SNAPE_GRASS(22879, 231, 61, 82, 82, 7, FlowerCrop.WHITE_LILY, new Item(995, 7500), 128, PlayerCounter.HARVESTED_SNAPE_GRASS);

	private final int seedId, produceId, levelReq;
	private final double plantXP, harvestXP;
	private final int totalStages;
	private final FlowerCrop protectionFlower;
	private final Item payment;
	private final int containerIndex;
	private final PlayerCounter counter;

	@Override
	public Item getPayment() {
		return payment;
	}
	
	public FlowerCrop getProtectionFlower() {
		return protectionFlower;
	}

	@Override
	public int getSeed() {
		return seedId;
	}

	@Override
	public int getLevelReq() {
		return levelReq;
	}

	@Override
	public long getStageTime() {
		return TimeUtils.getMinutesToMillis(10);
	}

	@Override
	public int getTotalStages() {
		return totalStages;
	}

	@Override
	public double getDiseaseChance(int compostType) {
		switch(compostType) {
			case 3:
				return 0.02;
			case 2:
				return 0.045;
			case 1:
				return 0.055;
			case 0:
			default:
				return 0.08;
		}
	}

	@Override
	public double getPlantXP() {
		return plantXP;
	}

	@Override
	public int getContainerIndex() {
		return containerIndex;
	}

	@Override
	public int getProduceId() {
		return produceId;
	}

	@Override
	public double getHarvestXP() {
		return harvestXP;
	}

	@Override
	public PlayerCounter getCounter() {
		return counter;
	}
}
