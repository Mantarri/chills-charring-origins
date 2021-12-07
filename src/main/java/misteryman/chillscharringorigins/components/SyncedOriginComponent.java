package misteryman.chillscharringorigins.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.github.apace100.origins.origin.Origin;
import misteryman.chillscharringorigins.common.registry.CCComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class SyncedOriginComponent implements OriginComponent, AutoSyncedComponent {
	private String originIdentifier = "default";
	private PlayerEntity pe;

	public SyncedOriginComponent(PlayerEntity pe) { this.pe = pe; }

	@Override public String getOrigin() { return this.originIdentifier; }
	@Override public void setOrigin(Origin origin) {
		if(origin != null && origin.getIdentifier() != Origin.EMPTY.getIdentifier()) {
			this.originIdentifier = origin.getIdentifier().toString();
			System.out.println("SetOrigin: " + origin.getIdentifier().toString());
			CCComponents.ORIGIN.sync(this.pe);
		}
	}
	@Override public void readFromNbt(NbtCompound tag) { this.originIdentifier = tag.getString("originIdentifier"); }
	@Override public void writeToNbt(NbtCompound tag) { tag.putString("originIdentifier", this.originIdentifier); }
}
