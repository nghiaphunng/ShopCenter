package shopcenter.com.core.usecase.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import shopcenter.com.core.entity.Permission;
import shopcenter.com.repository.PermissionRepository;
import shopcenter.com.adapter.dto.request.permission.PermissionRequest;
import shopcenter.com.adapter.dto.response.admin.permission.PermissionResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    //tạo permission
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse createPermission(PermissionRequest request){
        Permission permission = modelMapper.map(request, Permission.class);
        permission = permissionRepository.save(permission);

        return modelMapper.map(permission, PermissionResponse.class);
    }

    //lấy danh sách permission
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public List<PermissionResponse> getALlPermission(){
        var permissions = permissionRepository.findAll();

        return permissions.stream()
                .map(permission -> modelMapper.map(permission, PermissionResponse.class))
                .collect(Collectors.toList());
    }

    //xóa permission
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(String permissionName){
        permissionRepository.deleteById(permissionName);
    }
}
