package shopcenter.com.core.usecase.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import shopcenter.com.core.entity.Role;
import shopcenter.com.repository.PermissionRepository;
import shopcenter.com.repository.RoleRepository;
import shopcenter.com.adapter.dto.request.role.RoleRequest;
import shopcenter.com.adapter.dto.response.admin.role.RoleResponse;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    //tạo mới Role
    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse createRole(RoleRequest request){
        Role role = modelMapper.map(request, Role.class);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);

        return modelMapper.map(role, RoleResponse.class);
    }

    //lấy tất cả thông tin Role
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public List<RoleResponse> getAllRole(){
        var roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleResponse.class))
                .collect(Collectors.toList());
    }

    //xóa Role
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRole(String roleName){
        roleRepository.deleteById(roleName);
    }
}
