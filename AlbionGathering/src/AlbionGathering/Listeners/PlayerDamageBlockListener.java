package AlbionGathering.Listeners;

import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import AlbionGathering.Gathering.GatheringManager;
import AlbionGathering.Gathering.PlayerGathering;

public class PlayerDamageBlockListener extends PacketAdapter
{



	public PlayerDamageBlockListener(AdapterParameteters params) {
		super(params);
		// TODO Auto-generated constructor stub
	}

	public PlayerDamageBlockListener(Plugin plugin, Iterable<? extends PacketType> types) {
		super(plugin, types);
		// TODO Auto-generated constructor stub
	}

	public PlayerDamageBlockListener(Plugin plugin, ListenerPriority listenerPriority,
			Iterable<? extends PacketType> types, ListenerOptions... options) {
		super(plugin, listenerPriority, types, options);
		// TODO Auto-generated constructor stub
	}

	public PlayerDamageBlockListener(Plugin plugin, ListenerPriority listenerPriority,
			Iterable<? extends PacketType> types) {
		super(plugin, listenerPriority, types);
		// TODO Auto-generated constructor stub
	}

	public PlayerDamageBlockListener(Plugin plugin, ListenerPriority listenerPriority, PacketType... types) {
		super(plugin, listenerPriority, types);
		// TODO Auto-generated constructor stub
	}

	public PlayerDamageBlockListener(Plugin plugin, PacketType... types) {
		super(plugin, types);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onPacketReceiving(PacketEvent e) 
	{
		if(GatheringManager.playersGatheringMap.containsKey(e.getPlayer())) 
		{
			PlayerGathering pg = GatheringManager.playersGatheringMap.get(e.getPlayer());
			pg.tick();
		}
	}

}
