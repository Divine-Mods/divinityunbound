package name.divinityunbound.networking;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

public class DivinityUnboundFakePlayer {
    public static final GameProfile GAME_PROFILE = new GameProfile(UUID.nameUUIDFromBytes("fakeplayer.divinity_unbound".getBytes()),
            "fake_player.divinityunbound");
}
