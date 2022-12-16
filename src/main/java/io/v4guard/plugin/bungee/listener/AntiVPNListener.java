package io.v4guard.plugin.bungee.listener;

import io.v4guard.plugin.core.v4GuardCore;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class AntiVPNListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(PreLoginEvent e) {
        if(e.isCancelled()) return;
        if(e.getConnection() == null) return;
        if(ProxyServer.getInstance().getPlayer(e.getConnection().getName()) != null) return;
        v4GuardCore.getInstance().getCheckManager().runPreLoginCheck(e.getConnection().getName(), e);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPostLogin(PostLoginEvent e) {
        v4GuardCore.getInstance().getCheckManager().runPostLoginCheck(e.getPlayer().getName(), e);
    }

//    @EventHandler(priority = Byte.MIN_VALUE)
//    public void onChat(ChatEvent e) {
//        if(v4GuardCore.getInstance().getChatFilterManager().canLookupMessage(e.getMessage())){
//            Document data = new Document();
//            ProxiedPlayer player = (ProxiedPlayer) e.getSender();
//            data.put("username", player.getName());
//            data.put("location", v4GuardCore.getInstance().getCheckManager().getProcessors().get(0).getPlayerServer(player.getName()));
//            data.put("message", e.getMessage());
//            v4GuardCore.getInstance().getBackendConnector().getSocket().emit("chatfilter:chat", data.toJson());
//        }
//    }

}
