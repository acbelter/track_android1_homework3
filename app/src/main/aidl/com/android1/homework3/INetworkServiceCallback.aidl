// INetworkServiceCallback.aidl
package android1.homework3;

// Declare any non-default types here with import statements
interface INetworkServiceCallback {
    void onDataReceived(String data);
    void onConnected();
    void onConnectionFailed();
}
