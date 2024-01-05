package com.zj.auction.common.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.collect.Maps;
import com.zj.auction.common.oss.util.HttpUtil;

import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class OssUpload {
    public OssUpload() {
    }

    public static Map<String, Object> putObject(Long projectId, String fileNewName, InputStream input, Long fileSize) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("error_code", "-1");

        try {
            SimpleDateFormat smp = new SimpleDateFormat("yyy/MM/dd/HH/mm");
            fileNewName = "fy/"+smp.format(new Date()) + "/" + fileNewName;
            OSSClient ossClient = new OSSClient("oss-cn-beijing.aliyuncs.com", new DefaultCredentialProvider("qMRnWbBy2dUYya8G", "nPFFJkiw5DNawg1KpUINwQ1oeN2u2H"), null);
            PutObjectResult result = ossClient.putObject("zhongjiu-2022-06-08", fileNewName, input);
            ossClient.shutdown();
            String aliyunFullFilePath = "https://duoqio20180105.oss-cn-beijing.aliyuncs.com/" + fileNewName;
            res.put("error_code", "1");
            res.put("url", aliyunFullFilePath);
            ossFileBackup(projectId, fileSize, aliyunFullFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    private static void ossFileBackup(Long projectId, Long fileSize, String aliyunFullFilePath) {
        try {
            String encodeFilePath = URLEncoder.encode(aliyunFullFilePath, "UTF-8");
            String newUrl = "http://api.cq5869.com/file_cfg/addOssOk.action?fileOssTbl.projectId=" + projectId + "&&fileOssTbl.fileSize=" + fileSize + "&fileOssTbl.filePath=" + encodeFilePath;
            HttpUtil.get(newUrl, 0);
        } catch (Exception ignored) {
        }
    }
}
