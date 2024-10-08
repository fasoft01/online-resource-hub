package zw.co.fasoft.fileupload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
/**
 * @author Fasoft
 * @date 30/May/2024
 */
@RestController
@RequestMapping(value = "/resources",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Files & Resources", description = "files and resources")
@SecurityRequirement(name = "authorization")
@RequiredArgsConstructor
public class ResourceController {
    public final ResourceService resourceService;

    @PostMapping
    @Operation(description = "Save Resource")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<Resource>> create(
            @RequestBody List<ResourceRequest> request,
            Principal principal
    ) {
        return ResponseEntity.ok(resourceService.create(request,principal.getName()));
    }

    @PutMapping("/approve/{resource-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Approve Resource")
    public ResponseEntity<Resource> approve(
            @PathVariable("resource-id") Long resourceId
    ) {
        return ResponseEntity.ok(resourceService.approve(resourceId));
    }

    @PutMapping("/reject/{resource-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Reject Resource")
    public ResponseEntity<Resource> reject(
            @PathVariable("resource-id") Long resourceId,
            @RequestParam String reason
    ) {
        return ResponseEntity.ok(resourceService.reject(resourceId, reason));
    }

    @GetMapping("/profile")
    @Operation(description = "getting all resources created by logged in user")
    public ResponseEntity<Page<Resource>> getAllResources(
            Principal principal,
    @RequestParam(required = false, defaultValue = "0") int pageNumber,
    @RequestParam(required = false, defaultValue = "10") int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "createdOn") String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
            ){
        Sort.Direction direction = sortDir.equalsIgnoreCase(sortBy) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
        return ResponseEntity.ok(resourceService.getProfileResources(principal.getName(),pageable));
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(description = "getting all resources on student side")
    public ResponseEntity<Page<Resource>> getAllResourcesForStudent(
            @Nullable @RequestParam(required = false) String title,
            @Nullable @RequestParam(required = false) String description,
            @Nullable @RequestParam(required = false) String keywords,
            @Nullable @RequestParam(required = false) String contributorName,
            @Nullable @RequestParam(required = false)Long categoryId,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createdOn") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
    ) {
        Sort.Direction direction = sortDir.equalsIgnoreCase(sortBy) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
        return ResponseEntity.ok(resourceService.getAllResourcesForStudent(title, description, keywords, contributorName, categoryId, pageable));
    }

    @GetMapping("{id}")
    @Operation(description = "getting document by it's id number")
    public ResponseEntity<Resource> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.getById(id));
    }

    @DeleteMapping("{id}")
    @Operation(description = "deleting document by it's id number")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        resourceService.delete(id);
        return ResponseEntity.ok().body("Resource Deleted Successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Resource>> searchForResources
            (
                    @Nullable @RequestParam(required = true) String searchParam,
                    @RequestParam(required = false, defaultValue = "0") int pageNumber,
                    @RequestParam(required = false, defaultValue = "10") int pageSize,
                    @RequestParam(value = "sortBy", defaultValue = "createdOn") String sortBy,
                    @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
            ){
        Sort.Direction direction = sortDir.equalsIgnoreCase(sortBy) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
        return ResponseEntity.ok(resourceService.searchForResources(searchParam,pageable));
    }

    @GetMapping("/student/search")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Page<Resource>> searchResourcesForStudent
            (
                    @Nullable @RequestParam(required = true) String searchParam,
                    @RequestParam(required = false, defaultValue = "0") int pageNumber,
                    @RequestParam(required = false, defaultValue = "10") int pageSize,
                    @RequestParam(value = "sortBy", defaultValue = "createdOn") String sortBy,
                    @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
            ){
        Sort.Direction direction = sortDir.equalsIgnoreCase(sortBy) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
        return ResponseEntity.ok(resourceService.searchResourcesForStudent(searchParam,pageable));
    }
    @GetMapping
    public ResponseEntity<Page<Resource>> getAllResources
            (
                    @Nullable @RequestParam(required = false) String title,
                    @Nullable @RequestParam(required = false) String description,
                    @Nullable @RequestParam(required = false) String keywords,
                    @Nullable @RequestParam(required = false) String contributorName,
                    @Nullable @RequestParam(required = false)Long categoryId,
                    @RequestParam(required = false, defaultValue = "0") int pageNumber,
                    @RequestParam(required = false, defaultValue = "10") int pageSize,
                    @RequestParam(value = "sortBy", defaultValue = "createdOn") String sortBy,
                    @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
            ){
        Sort.Direction direction = sortDir.equalsIgnoreCase(sortBy) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
        return ResponseEntity.ok(resourceService.getAllResources(contributorName,title,description,keywords,categoryId,pageable));
    }

}