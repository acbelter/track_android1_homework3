package com.android1.homework3.msg.request;

import com.android1.homework3.msg.BaseMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONMessageBuilder implements MessageBuilder {
    @Override
    public String buildMessage(BaseMessage data) {
        try {
            if (data.getClass() == AuthRequestMessage.class) {
                return buildAuthMessage((AuthRequestMessage) data);
            }

            if (data.getClass() == ChannelListRequestMessage.class) {
                return buildChannelListMessage((ChannelListRequestMessage) data);
            }

            if (data.getClass() == CreateChannelRequestMessage.class) {
                return buildCreateChannelMessage((CreateChannelRequestMessage) data);
            }

            if (data.getClass() == EnterRequestMessage.class) {
                return buildEnterMessage((EnterRequestMessage) data);
            }

            if (data.getClass() == LeaveRequestMessage.class) {
                return buildLeaveMessage((LeaveRequestMessage) data);
            }

            if (data.getClass() == RegisterRequestMessage.class) {
                return buildRegisterMessage((RegisterRequestMessage) data);
            }

            if (data.getClass() == SendRequestMessage.class) {
                return buildSendMessage((SendRequestMessage) data);
            }

            if (data.getClass() == SetUserInfoRequestMessage.class) {
                return buildSetUserInfoMessage((SetUserInfoRequestMessage) data);
            }

            if (data.getClass() == UserInfoRequestMessage.class) {
                return buildUserInfoMessage((UserInfoRequestMessage) data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String buildAuthMessage(AuthRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "auth");

        JSONObject data = new JSONObject();
        data.put("login", message.login);
        data.put("pass", message.pass);
        json.put("data", data);
        return null;
    }

    private String buildChannelListMessage(ChannelListRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "channellist");

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        json.put("data", data);
        return null;
    }

    private String buildCreateChannelMessage(CreateChannelRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "createchannel");

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        data.put("name", message.name);
        data.put("descr", message.descr);
        json.put("data", data);
        return null;
    }

    private String buildEnterMessage(EnterRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "enter");

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        data.put("channel", message.channel);
        json.put("data", data);
        return null;
    }

    private String buildLeaveMessage(LeaveRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "leave");

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        data.put("channel", message.channel);
        json.put("data", data);
        return null;
    }

    private String buildRegisterMessage(RegisterRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "register");

        JSONObject data = new JSONObject();
        data.put("login", message.login);
        data.put("pass", message.pass);
        data.put("nick", message.nick);
        json.put("data", data);
        return null;
    }

    private String buildSendMessage(SendRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "message");

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        data.put("channel", message.channel);
        data.put("body", message.body);
        json.put("data", data);
        return null;
    }

    private String buildSetUserInfoMessage(SetUserInfoRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "setuserinfo");

        JSONObject data = new JSONObject();
        data.put("user_status", message.userStatus);
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        json.put("data", data);
        return null;
    }

    private String buildUserInfoMessage(UserInfoRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", "userinfo");

        JSONObject data = new JSONObject();
        data.put("user", message.user);
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        json.put("data", data);
        return null;
    }
}
