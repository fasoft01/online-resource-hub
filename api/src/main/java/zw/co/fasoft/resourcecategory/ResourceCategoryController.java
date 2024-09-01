package zw.co.fasoft.resourcecategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.fasoft.resourcecategories.ResourceCategoryResponse;
import zw.co.fasoft.resourcecategories.ResourceCategoryService;

import java.security.Principal;
import java.util.List;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/resource-category", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Resource Categories", description = "resource category controller")
@SecurityRequirement(name = "authorization")
public class ResourceCategoryController {
    private final ResourceCategoryService resourceCategoryService;

    @PostMapping
    @Operation(description = "Create resource category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResourceCategoryResponse> createResourceCategory(
            @RequestBody ResourceCategoryRequest request) {
        return ResponseEntity.ok(resourceCategoryService.createResourceCategory(request));
    }

    @PostMapping("/preferred")
    @Operation(description = "Add preferred resource category")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> addPreferredResourceCategory(
            @RequestParam Long resourceCategoryId,
            Principal principal) {
        resourceCategoryService.addPreferredResourceCategory(resourceCategoryId,principal.getName());
        return ResponseEntity.ok("Resource Category Added Successfully");
    }

    @GetMapping("/preferred")
    @Operation(description = "Get preferred resource category")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<ResourceCategoryResponse>> getPreferredResourceCategory(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false)  String name,
            Principal principal) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(resourceCategoryService.getPreferredCategories(name,pageable,principal.getName()));
    }

    @PutMapping
    @Operation(description = "Update resource category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResourceCategoryResponse> updateResourceCategory(
            @RequestBody ResourceCategoryRequest request,
            @RequestParam Long resourceCategoryId) {
        return ResponseEntity.ok(resourceCategoryService.updateResourceCategory(resourceCategoryId,request));
    }

    @DeleteMapping
    @Operation(description = "Delete resource category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteResourceCategory(@RequestParam Long id) {
        resourceCategoryService.deleteResourceCategory(id);
        return ResponseEntity.ok().body("Resource Category Deleted Successfully");
    }

    @GetMapping("/{id}")
    @Operation(description = "Get resource category by id")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<ResourceCategory> getResourceCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(resourceCategoryService.getResourceCategoryById(id));
    }

    @GetMapping
    @Operation(description = "Get all resource categories")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<Page<ResourceCategoryResponse>> getAllResourceCategories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            Principal principal) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(resourceCategoryService.getAllResourceCategories(name,pageable));
    }

//    @GetMapping
//    @Operation(description = "Get all resource categories")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Page<ResourceCategory>> getAllResourceCategories(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false, defaultValue = "0") int pageNumber,
//            @RequestParam(required = false, defaultValue = "10") int pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        return ResponseEntity.ok(resourceCategoryService.getAllResourceCategories(name,pageable));
//    }
}
