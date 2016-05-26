// INetworkService.aidl
package android1.homework3;

import com.android1.homework3.INetworkServiceCallback;

// Declare any non-default types here with import statements
interface INetworkService {
    oneway void sendMessage(String message);
    void setCallback(INetworkServiceCallback callback);
}
