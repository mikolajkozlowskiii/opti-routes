package com.wroclawroutes.routes.component.impl;

import com.wroclawroutes.routes.component.TSPSolver;
import com.wroclawroutes.routes.dto.*;
import com.wroclawroutes.routes.entity.Location;
import com.wroclawroutes.routes.entity.LocationConnection;
import com.wroclawroutes.routes.repository.RouteRepository;
import com.wroclawroutes.routes.service.mapper.LocationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class RouteStepsOptimizerImplTest {
    private RouteStepsOptimizerImpl routeStepsOptimizer;
    private TSPSolver tspSolver;
    private TSPDataMapper tspDataMapper;

    @BeforeEach
    public void setup(){
        tspDataMapper = new TSPDataMapper();
        tspSolver = new TSPSolverGoogleORTools();
        routeStepsOptimizer = new RouteStepsOptimizerImpl(tspSolver, tspDataMapper);
    }
    @Test
    public void testGetOptimizedSteps() {
        // Arrange: Prepare test data
        final LocationDTO locationA = LocationDTO.builder().name("A").latitude(2.1).longitude(2.2).build();
        final LocationDTO locationB = LocationDTO.builder().name("B").latitude(2.5).longitude(2.9).build();
        final LocationDTO locationC = LocationDTO.builder().name("C").latitude(3.1).longitude(3.2).build();
        final LocationDTO locationD = LocationDTO.builder().name("D").latitude(5.1).longitude(5.2).build();

        final LocationConnectionDTO locationConnectionAB = LocationConnectionDTO
                .builder()
                .startLocation(locationA)
                .endLocation(locationB)
                .timeInMiliSeconds(5)
                .build();

        final LocationConnectionDTO locationConnectionBA = LocationConnectionDTO
                .builder()
                .startLocation(locationB)
                .endLocation(locationA)
                .timeInMiliSeconds(5)
                .build();

        final LocationConnectionDTO locationConnectionAC = LocationConnectionDTO
                .builder()
                .startLocation(locationA)
                .endLocation(locationC)
                .timeInMiliSeconds(10)
                .build();


        final LocationConnectionDTO locationConnectionCA = LocationConnectionDTO
                .builder()
                .startLocation(locationC)
                .endLocation(locationA)
                .timeInMiliSeconds(10)
                .build();


        final LocationConnectionDTO locationConnectionAD = LocationConnectionDTO
                .builder()
                .startLocation(locationA)
                .endLocation(locationD)
                .timeInMiliSeconds(100)
                .build();


        final LocationConnectionDTO locationConnectionDA = LocationConnectionDTO
                .builder()
                .startLocation(locationD)
                .endLocation(locationA)
                .timeInMiliSeconds(100)
                .build();

        final LocationConnectionDTO locationConnectionBC = LocationConnectionDTO
                .builder()
                .startLocation(locationB)
                .endLocation(locationC)
                .timeInMiliSeconds(20)
                .build();


        final LocationConnectionDTO locationConnectionCB = LocationConnectionDTO
                .builder()
                .startLocation(locationC)
                .endLocation(locationB)
                .timeInMiliSeconds(20)
                .build();

        final LocationConnectionDTO locationConnectionBD = LocationConnectionDTO
                .builder()
                .startLocation(locationB)
                .endLocation(locationD)
                .timeInMiliSeconds(30)
                .build();

        final LocationConnectionDTO locationConnectionDB = LocationConnectionDTO
                .builder()
                .startLocation(locationD)
                .endLocation(locationB)
                .timeInMiliSeconds(30)
                .build();
        final LocationConnectionDTO locationConnectionCD = LocationConnectionDTO
                .builder()
                .startLocation(locationC)
                .endLocation(locationD)
                .timeInMiliSeconds(40)
                .build();

        final LocationConnectionDTO locationConnectionDC = LocationConnectionDTO
                .builder()
                .startLocation(locationD)
                .endLocation(locationC)
                .timeInMiliSeconds(40)
                .build();


        OptimizedStepsRequest request = OptimizedStepsRequest
                .builder()
                .depot(locationA)
                .locationConnections(Set.of(locationConnectionAB, locationConnectionBA, locationConnectionAC, locationConnectionCA,
                        locationConnectionAD, locationConnectionDA, locationConnectionBC, locationConnectionCB, locationConnectionBD,
                        locationConnectionDB, locationConnectionCD, locationConnectionDC))
                .build();

        // Act: Call the method to be tested
        OptimizedStepsResponse result = routeStepsOptimizer.getOptimizedSteps(request);

        System.out.println(result);
        // Assert: Verify the result or state after the method call
        // Add assertions as per your test requirements
    }
}