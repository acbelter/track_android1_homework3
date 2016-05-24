package com.android1.homework3.msg.response;

import com.android1.homework3.Logger;
import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;
import com.android1.homework3.msg.Status;
import com.android1.homework3.msg.request.SendRequestMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;

public class JSONMessageParser implements MessageParser {
    @Override
    public BaseMessage parseMessage(String data)  {
        if (data == null) {
            return null;
        }

        try {
            JSONObject json = new JSONObject(data);
            if (!json.has("action")) {
                return null;
            }

            String action = json.getString("action");
            switch (action) {
                case MessageAction.AUTH: {
                    return parseAuthMessage(json);
                }
                case MessageAction.CHANNEL_LIST: {
                    return parseChannelListMessage(json);
                }
                case MessageAction.CREATE_CHANNEL: {
                    return parseCreateChannelMessage(json);
                }
                case MessageAction.EVENT_ENTER: {
                    return parseEnterEventMessage(json);
                }
                case MessageAction.ENTER: {
                    return parseEnterMessage(json);
                }
                case MessageAction.EVENT_LEAVE: {
                    return parseLeaveEventMessage(json);
                }
                case MessageAction.LEAVE: {
                    return parseLeaveMessage(json);
                }
                case MessageAction.EVENT_MESSAGE: {
                    return parseMessageEventMessage(json);
                }
                case MessageAction.REGISTER: {
                    return parseRegisterMessage(json);
                }
                case MessageAction.SET_USER_INFO: {
                    return parseSetUserInfoMessage(json);
                }
                case MessageAction.USER_INFO: {
                    return parseUserInfoMessage(json);
                }
                case MessageAction.WELCOME: {
                    return parseWelcomeMessage(json);
                }
                // TODO This message should not received!
                case MessageAction.SEND_MESSAGE: {
                    return new SendRequestMessage();
                }
            }
        } catch (JSONException e) {
            Logger.d("JSONException: " + e.getMessage());
        }
        return null;
    }

    private AuthResponseMessage parseAuthMessage(JSONObject json) throws JSONException {
        AuthResponseMessage message = new AuthResponseMessage();
        JSONObject data = json.getJSONObject("data");

        message.status = Status.values()[data.getInt("status")];
        message.error = data.optString("error");
        message.sid = data.optString("sid");
        message.cid = data.optString("cid");
        return message;
    }

    private ChannelListResponseMessage parseChannelListMessage(JSONObject json) throws JSONException {
        ChannelListResponseMessage message = new ChannelListResponseMessage();
        JSONObject data = json.getJSONObject("data");

        message.status = Status.values()[data.getInt("status")];
        message.error = data.optString("error");

        if (data.has("channels")) {
            JSONArray channels = data.getJSONArray("channels");
            for (int i = 0; i < channels.length(); i++) {
                Channel channel = parseChannel(channels.getJSONObject(i));
                message.channels.add(channel);
            }
        }

        Collections.sort(message.channels, new Comparator<Channel>() {
            @Override
            public int compare(Channel lhs, Channel rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        return message;
    }

    private Channel parseChannel(JSONObject json) throws JSONException {
        Channel channel = new Channel();
        channel.chid = json.getString("chid");
        channel.name = json.getString("name");
        channel.descr = json.getString("descr");
        channel.online = json.getInt("online");
        return channel;
    }

    private CreateChannelResponseMessage parseCreateChannelMessage(JSONObject json) throws JSONException {
        CreateChannelResponseMessage message = new CreateChannelResponseMessage();
        JSONObject data = json.getJSONObject("data");

        message.status = Status.values()[data.getInt("status")];
        message.error = data.optString("error");
        message.chid = data.optString("chid");
        return message;
    }

    private EnterEventMessage parseEnterEventMessage(JSONObject json) throws JSONException {
        EnterEventMessage message = new EnterEventMessage();
        JSONObject data = json.getJSONObject("data");

        message.chid = data.getString("chid");
        message.uid = data.getString("uid");
        message.nick = data.getString("nick");
        return message;
    }

    private EnterResponseMessage parseEnterMessage(JSONObject json) throws JSONException {
        EnterResponseMessage message = new EnterResponseMessage();
        JSONObject data = json.getJSONObject("data");

        message.status = Status.values()[data.getInt("status")];
        message.error = data.optString("error");

        if (data.has("users")) {
            JSONArray users = data.getJSONArray("users");
            for (int i = 0; i < users.length(); i++) {
                User user = parseUser(users.getJSONObject(i));
                message.users.add(user);
            }
        }

        if (data.has("last_msg")) {
            JSONArray lastMessages = data.getJSONArray("last_msg");
            for (int i = 0; i < lastMessages.length(); i++) {
                LastMessage msg = parseLastMessage(lastMessages.getJSONObject(i));
                message.lastMsg.add(msg);
            }
        }

        return message;
    }

    private User parseUser(JSONObject json) throws JSONException {
        User user = new User();
        user.uid = json.getString("uid");
        user.nick = json.getString("nick");
        return user;
    }

    private LastMessage parseLastMessage(JSONObject json) throws JSONException {
        LastMessage lastMessage = new LastMessage();
        lastMessage.mid = json.getString("mid");
        lastMessage.from = json.getString("from");
        lastMessage.nick = json.getString("nick");
        lastMessage.body = json.getString("body");
        lastMessage.time = json.getLong("time");
        return lastMessage;
    }

    private LeaveEventMessage parseLeaveEventMessage(JSONObject json) throws JSONException {
        LeaveEventMessage message = new LeaveEventMessage();
        JSONObject data = json.getJSONObject("data");

        message.chid = data.getString("chid");
        message.uid = data.getString("uid");
        message.nick = data.getString("nick");
        return message;
    }

    private LeaveResponseMessage parseLeaveMessage(JSONObject json) throws JSONException {
        LeaveResponseMessage message = new LeaveResponseMessage();
        JSONObject data = json.getJSONObject("data");

        message.status = Status.values()[data.getInt("status")];
        message.error = data.optString("error");
        return message;
    }

    private MessageEventMessage parseMessageEventMessage(JSONObject json) throws JSONException {
        MessageEventMessage message = new MessageEventMessage();
        JSONObject data = json.getJSONObject("data");

        message.chid = data.getString("chid");
        message.from = data.getString("from");
        message.nick = data.getString("nick");
        message.body = data.getString("body");
        return message;
    }

    private RegisterResponseMessage parseRegisterMessage(JSONObject json) throws JSONException {
        RegisterResponseMessage message = new RegisterResponseMessage();
        JSONObject data = json.getJSONObject("data");

        message.status = Status.values()[data.getInt("status")];
        message.error = data.optString("error");
        return message;
    }

    private SetUserInfoResponseMessage parseSetUserInfoMessage(JSONObject json) throws JSONException {
        SetUserInfoResponseMessage message = new SetUserInfoResponseMessage();
        JSONObject data = json.getJSONObject("data");

        message.status = Status.values()[data.getInt("status")];
        message.error = data.optString("error");
        return message;
    }

    private UserInfoResponseMessage parseUserInfoMessage(JSONObject json) throws JSONException {
        UserInfoResponseMessage message = new UserInfoResponseMessage();
        JSONObject data = json.getJSONObject("data");

        message.status = Status.values()[data.getInt("status")];
        message.error = data.optString("error");
        message.nick = data.optString("nick");
        message.userStatus = data.optString("user_status");
        return message;
    }

    private WelcomeMessage parseWelcomeMessage(JSONObject json) throws JSONException {
        WelcomeMessage message = new WelcomeMessage();
        message.message = json.getString("message");
        message.time = json.getLong("time");
        return message;
    }
}
