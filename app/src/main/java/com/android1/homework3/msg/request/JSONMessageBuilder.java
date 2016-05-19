package com.android1.homework3.msg.request;

import com.android1.homework3.Logger;
import com.android1.homework3.msg.BaseMessage;
import com.android1.homework3.msg.MessageAction;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONMessageBuilder implements MessageBuilder {
    @Override
    public String buildMessage(BaseMessage data) {
        Logger.d("Build message for action: " + data.getAction());
        try {
            switch (data.getAction()) {
                case MessageAction.AUTH: {
                    return buildAuthMessage((AuthRequestMessage) data);
                }
                case MessageAction.CHANNEL_LIST: {
                    return buildChannelListMessage((ChannelListRequestMessage) data);
                }
                case MessageAction.CREATE_CHANNEL: {
                    return buildCreateChannelMessage((CreateChannelRequestMessage) data);
                }
                case MessageAction.ENTER: {
                    return buildEnterMessage((EnterRequestMessage) data);
                }
                case MessageAction.LEAVE: {
                    return buildLeaveMessage((LeaveRequestMessage) data);
                }
                case MessageAction.REGISTER: {
                    return buildRegisterMessage((RegisterRequestMessage) data);
                }
                case MessageAction.SEND_MESSAGE: {
                    return buildSendMessage((SendRequestMessage) data);
                }
                case MessageAction.SET_USER_INFO: {
                    return buildSetUserInfoMessage((SetUserInfoRequestMessage) data);
                }
                case MessageAction.USER_INFO: {
                    return buildUserInfoMessage((UserInfoRequestMessage) data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildAuthMessage(AuthRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.AUTH);

        JSONObject data = new JSONObject();
        data.put("login", message.login);
        data.put("pass", message.pass);
        json.put("data", data);
        return json.toString();
    }

    private String buildChannelListMessage(ChannelListRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.CHANNEL_LIST);

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        json.put("data", data);
        return json.toString();
    }

    private String buildCreateChannelMessage(CreateChannelRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.CREATE_CHANNEL);

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        data.put("name", message.name);
        data.put("descr", message.descr);
        json.put("data", data);
        return json.toString();
    }

    private String buildEnterMessage(EnterRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.ENTER);

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        data.put("channel", message.channel);
        json.put("data", data);
        return json.toString();
    }

    private String buildLeaveMessage(LeaveRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.LEAVE);

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        data.put("channel", message.channel);
        json.put("data", data);
        return json.toString();
    }

    private String buildRegisterMessage(RegisterRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.REGISTER);

        JSONObject data = new JSONObject();
        data.put("login", message.login);
        data.put("pass", message.pass);
        data.put("nick", message.nick);
        json.put("data", data);
        return json.toString();
    }

    private String buildSendMessage(SendRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.SEND_MESSAGE);

        JSONObject data = new JSONObject();
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        data.put("channel", message.channel);
        data.put("body", message.body);
        json.put("data", data);
        return json.toString();
    }

    private String buildSetUserInfoMessage(SetUserInfoRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.SET_USER_INFO);

        JSONObject data = new JSONObject();
        data.put("user_status", message.userStatus);
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        json.put("data", data);
        return json.toString();
    }

    private String buildUserInfoMessage(UserInfoRequestMessage message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("action", MessageAction.USER_INFO);

        JSONObject data = new JSONObject();
        data.put("user", message.user);
        data.put("cid", message.cid);
        data.put("sid", message.sid);
        json.put("data", data);
        return json.toString();
    }
}
