package com.BeaconManager.application.rosPublisher;

import com.BeaconManager.beaconService.location.Position;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
//import org.ros.message.MessageFactory;
import org.ros.node.topic.Publisher;

import geometry_msgs.Pose2D;


/**
 * Created by Kevin on 11/10/2017.
 */

public class BeaconPublisher extends AbstractNodeMain {
    // Localised position;
    private Position position = new Position(0,0);

    public BeaconPublisher() {
        super();
    }

    public void update(double x, double y) {
        position.update(x, y);
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        super.onStart(connectedNode);
        // Before creating publisher, getInstance of BeaconService;

        final Publisher<geometry_msgs.Pose2D> publisher =
                connectedNode.newPublisher("Position", Pose2D._TYPE);
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private int sequenceNumber;
            @Override
            protected void setup() {
                sequenceNumber = 0;
            }

            @Override
            protected void loop() throws InterruptedException {
                // Publish a pose
                geometry_msgs.Pose2D pose = publisher.newMessage();
                pose.setX(position.x());
                pose.setY(position.y());
                // No orientation detection scoped
                pose.setTheta(0.0);
                sequenceNumber++;
                Thread.sleep(1000);
            }
        });
    }

    @Override
    public void onShutdown(Node node) {
        super.onShutdown(node);
    }

    @Override
    public void onShutdownComplete(Node node) {
        super.onShutdownComplete(node);
    }

    @Override
    public void onError(Node node, Throwable throwable) {
        super.onError(node, throwable);
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("BeaconData");
    }
}
