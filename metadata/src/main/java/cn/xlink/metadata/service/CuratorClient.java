package cn.xlink.metadata.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ghold on 2017/7/5.
 */
public class CuratorClient {
    private CuratorFramework client;
    private String root;
    private static final Logger LOG = LoggerFactory.getLogger(CuratorClient.class);

    public CuratorClient(String connect, String root, int times, int gap){
        try {
            this.client = CuratorFrameworkFactory.newClient(connect, new RetryNTimes(times, gap));
            this.client.start();
            this.root = root;
            this.init();
        } catch (Exception e) {
            LOG.error("zookeeper exception: %s", e.getMessage());
        }
    }

    private void init() throws Exception {
        String[] nodes = this.root.split("/");
        StringBuilder path = new StringBuilder();
        for (String node: nodes) {
            if (node.equals("")) continue;
            path.append("/").append(node);
            if (client.checkExists().forPath(path.toString()) == null) {
                client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path.toString());
            }
        }
    }

    public void close(String path) {
        try{
            if (this.client.checkExists().forPath(path) != null) {
                this.client.delete().withVersion(-1).forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.client.close();
        }
    }

    public void close() {
        this.client.close();
    }

    public void create(String path) throws Exception{
        path = this.root + "/" + path;
        if (client.checkExists().forPath(path) == null) {
            this.client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path);
        }
    }

    public CuratorFramework getClient() {
        return client;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }
}
