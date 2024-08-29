package zw.co.fasoft.administration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.fasoft.requests.UserAccountRequest;
import zw.co.fasoft.responses.UserAccountResponse;
import zw.co.fasoft.useraccount.UserAccount;
import zw.co.fasoft.utils.enums.Status;
import zw.co.fasoft.utils.enums.UserGroup;

import java.security.Principal;

/**
 * @author Fasoft
 * @date 30/May/2024
 */
@Data
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Administration Controller", description = "Controller for performing administrative duties")
@SecurityRequirement(name = "authorization")
public class AdministrationController {

    private final AdministrationService administrationService;

    @PostMapping
    @Operation(description = "Creates User")
    public ResponseEntity<UserAccount> createUser(
            @RequestBody UserAccountRequest userAccountRequest
            ){
        return ResponseEntity.ok(administrationService.createUser(userAccountRequest));
    }

    @PutMapping("/user")
    @Operation(description = "User update Profile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAccount> updateUserAccount(
            Principal principal,
            @RequestBody UpdateUserAccountRequest request){
        return ResponseEntity.ok(administrationService.updateUserProfile(principal.getName(),request));
    }

    @GetMapping("/profile")
    @Operation(description = "Gets User Profile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAccount> getUserProfile(Principal principal){
        return ResponseEntity.ok(administrationService.getUserProfile(principal.getName()));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Deletes User by ID")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        administrationService.deleteUser(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }
    @GetMapping
    @Operation(description = "Gets All Users")
    public ResponseEntity<Page<UserAccountResponse>> getAll(
            @Nullable @RequestParam String name,
            @Nullable @RequestParam Status status,
            @Nullable @RequestParam UserGroup role,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createdOn") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir
    ){
        Sort.Direction direction = sortDir.equalsIgnoreCase(sortBy) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));

        return ResponseEntity.ok(administrationService.getAllUsers(name,status,role,pageable));
    }

}