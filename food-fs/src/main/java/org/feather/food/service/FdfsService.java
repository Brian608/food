package org.feather.food.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: FdfsService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-15 16:22
 * @version: 1.0
 */
public interface FdfsService {

     String upload(MultipartFile file, String fileExtName) throws Exception;

     String uploadOSS(MultipartFile file, String userId, String fileExtName) throws Exception;
}
