package com.dootie.turtles.util;

import com.google.common.collect.ImmutableSetMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class Skull {

    private static Method metaSetProfileMethod;
    private static Field metaSetProfileField;

    public static ItemStack getCustomTextureHead(String name, String texture) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (!(head.getItemMeta() instanceof SkullMeta meta)) {
            return null;
        }
        mutateItemMeta(meta, "§f" + name, texture);
        head.setItemMeta(meta);
        return head;
    }

    private static void mutateItemMeta(SkullMeta meta, String name, String texture) {
        try {
            if (metaSetProfileMethod == null) {
                metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                metaSetProfileMethod.setAccessible(true);
            }
            metaSetProfileMethod.invoke(meta, makeProfile(texture));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            try {
                if (metaSetProfileField == null) {
                    metaSetProfileField = meta.getClass().getDeclaredField("profile");
                    metaSetProfileField.setAccessible(true);
                }
                metaSetProfileField.set(meta, makeProfile(texture));

            } catch (NoSuchFieldException | IllegalAccessException ex2) {
                ex2.printStackTrace();
            }
        }
        meta.setAttributeModifiers(ImmutableSetMultimap.of());
        meta.setDisplayName(name);
    }

    private static GameProfile makeProfile(String texture) {
        UUID id = new UUID(texture.substring(texture.length() - 20).hashCode(), texture.substring(texture.length() - 10).hashCode());
        GameProfile profile = new GameProfile(id, "Player");
        profile.getProperties().put("textures", new Property("textures", texture));
        return profile;
    }
}
