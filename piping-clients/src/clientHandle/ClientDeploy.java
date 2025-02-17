package clientHandle;

import Entity.PortModel;

public class ClientDeploy {
    private final PortModel portModel;
    public ClientDeploy() {
        portModel = new PortModel();
    }
    public void run() {
        Handle handle=new Handle(portModel);
        handle.clientManage();
    }
}

