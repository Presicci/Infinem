package io.ruin.model.item.actions.impl.tradepost;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import io.ruin.model.item.actions.impl.tradepost.offer.TradePostNPCOffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/1/2024
 */
public class ExchangeNPCOffers {

    @Expose
    protected final List<TradePostNPCOffer> OFFERS = new ArrayList<>();

    public static ExchangeNPCOffers INSTANCE = new ExchangeNPCOffers();

    private static final Gson GSON_LOADER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static final String PATH = "../data/ge/npc_offers.json";

    public static void load() {
        try {
            File saveFile = new File(PATH);
            if (saveFile.exists()) {
                String json = new String(Files.readAllBytes(saveFile.toPath()));
                INSTANCE = GSON_LOADER.fromJson(json, ExchangeNPCOffers.class);
                System.out.println("Loaded " + INSTANCE.OFFERS.size() + " grand exchange npc listings");
            }
        } catch (IOException e) {
            System.err.println("Couldn't load grand exchange npc listings. " + e.getMessage());
        }
    }

    static {
        load();
    }
}
